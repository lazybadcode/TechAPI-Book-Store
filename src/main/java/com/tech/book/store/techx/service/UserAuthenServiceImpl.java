package com.tech.book.store.techx.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.book.store.techx.controller.request.UserAuthenRequest;
import com.tech.book.store.techx.exception.UserException;
import com.tech.book.store.techx.model.UserAuthen;
import com.tech.book.store.techx.repository.UserAuthenRepository;


@Service
public class UserAuthenServiceImpl implements UserAuthenService {

	private final UserAuthenRepository userAuthenRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserAuthenServiceImpl(UserAuthenRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userAuthenRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserAuthen register(UserAuthenRequest userRequest) {
		UserAuthen user = userAuthenRepository.findByUsername(userRequest.getUsername());
		if (user == null) {
			user = new UserAuthen().setUsername(userRequest.getUsername())
					.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()))
					.setRole(userRequest.getRole());

			return userAuthenRepository.save(user);
		}
		throw new UserException(userRequest.getUsername());
	}

	@Override
	public UserAuthen findUserByUsername(String username) {
		Optional<UserAuthen> user = Optional.ofNullable(userAuthenRepository.findByUsername(username));
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

}
