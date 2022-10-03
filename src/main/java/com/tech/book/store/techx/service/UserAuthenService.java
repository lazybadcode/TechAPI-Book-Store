package com.tech.book.store.techx.service;

import com.tech.book.store.techx.controller.request.UserAuthenRequest;
import com.tech.book.store.techx.model.UserAuthen;

public interface UserAuthenService {
	UserAuthen register(UserAuthenRequest userRequest);

	UserAuthen findUserByUsername(String username);
}
