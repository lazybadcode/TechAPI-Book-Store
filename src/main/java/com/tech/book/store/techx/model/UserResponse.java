package com.tech.book.store.techx.model;

import java.util.List;

import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

	public UserResponse(String username, String password, String date_of_birth, List<Integer> books) {
		super();
		this.username = username;
		this.password = password;
		this.date_of_birth = date_of_birth;
		this.books = books;
	}

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String date_of_birth;
	
	@Column(nullable = true)
	private List<Integer> books;
}
