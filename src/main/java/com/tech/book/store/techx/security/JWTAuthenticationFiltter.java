package com.tech.book.store.techx.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.book.store.techx.controller.request.UserAuthenRequest;
import com.tech.book.store.techx.config.Cons;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFiltter extends UsernamePasswordAuthenticationFilter {

	@Value("${app.token.issuer}")
	private String issuer;
	
	@Value("${app.token.audience}")
	private String audience;
	
	
	private final AuthenticationManager authenticationManager;

	public JWTAuthenticationFiltter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;

		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/users/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UserAuthenRequest userRequest = new ObjectMapper().readValue(request.getInputStream(), UserAuthenRequest.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),
					userRequest.getPassword(), new ArrayList<>()));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		if (authResult.getPrincipal() != null) {
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
					.getPrincipal();

			String username = user.getUsername();

			if (username != null && username.length() > 0) {
				Claims claims = Jwts.claims().setSubject(username).setIssuer(issuer).setAudience(audience);

				List<String> roles = new ArrayList<>();
				user.getAuthorities().stream().forEach(authority -> roles.add(authority.getAuthority()));

				claims.put("role", roles);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				Map<String, Object> responseJSON = new HashMap<>();
				responseJSON.put("token", createToken(claims));

				OutputStream out = response.getOutputStream();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writerWithDefaultPrettyPrinter().writeValue(out, responseJSON);

				out.flush();
			}
		}
	}

	private String createToken(Claims claims) {
		return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + Cons.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, Cons.SECRET_KEY).compact();
	}

}
