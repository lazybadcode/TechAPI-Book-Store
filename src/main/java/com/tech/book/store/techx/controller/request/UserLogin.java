package com.tech.book.store.techx.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Service
@NoArgsConstructor
public class UserLogin {
	@NotEmpty
	@Size(min = 1, max = 100)
	private String username;

	@NotEmpty
	private String password;
}
