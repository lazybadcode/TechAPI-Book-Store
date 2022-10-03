package com.tech.book.store.techx.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Service
@NoArgsConstructor
public class UserAuthenRequest {

	@NotEmpty
	@Size(min = 1, max = 100)
	private String username;

	@NotEmpty
	@Length(min = 8, message = "The password must be at least {min} characters")
	private String password;

	@NotEmpty
	private String role;

	public UserAuthenRequest(String username,
			String password,
			String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	
}

