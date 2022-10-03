package com.tech.book.store.techx.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.tech.book.store.techx.config.Cons;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith(Cons.TOKEN_PREFIX)) {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String jwt) {
		
		Claims claims = Jwts.parser().setSigningKey(Cons.SECRET_KEY).parseClaimsJws(jwt.replace(Cons.TOKEN_PREFIX, "")).getBody();

		String username = claims.getSubject();
		if (username == null) {
			return null;
		}

		ArrayList<String> roles = (ArrayList<String>) claims.get("role");
		ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if (roles != null) {
			for (String role : roles) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}
		}

		return new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
	}

}
