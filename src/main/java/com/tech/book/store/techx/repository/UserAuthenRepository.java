package com.tech.book.store.techx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.book.store.techx.model.UserAuthen;


public interface UserAuthenRepository extends JpaRepository<UserAuthen, Long> {
	// select * from user where username = 'techx'
	UserAuthen findByUsername(String username);
}
