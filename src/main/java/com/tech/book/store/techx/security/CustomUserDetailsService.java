package com.tech.book.store.techx.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tech.book.store.techx.model.UserAuthen;
import com.tech.book.store.techx.service.UserAuthenService;
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserAuthenService userService;

	public CustomUserDetailsService(UserAuthenService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAuthen user = userService.findUserByUsername(username);
		if (user != null) {
			Set<GrantedAuthority> roles = new HashSet<>();
			roles.add(new SimpleGrantedAuthority(user.getRole()));

			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roles);

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					authorities);
		} else {
			throw new UsernameNotFoundException("Username: " + username + "does not exist.");
		}
	}

}

