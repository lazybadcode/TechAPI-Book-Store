package com.tech.book.store.techx.controller.request;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Service
@NoArgsConstructor
public class UsersRequest {
	@NotEmpty
	private String username;

	@NotEmpty
	@Length(min = 8, message = "The password must be at least {min} characters")
	private String password;

	@NotEmpty
	private String date_of_birth;

	public UsersRequest(@NotEmpty String username,
			@NotEmpty @Length(min = 8, message = "The password must be at least {min} characters") String password,
			@NotEmpty String date_of_birth) {
		super();
		this.username = username;
		this.password = password;
		this.date_of_birth = date_of_birth;
	}
}
