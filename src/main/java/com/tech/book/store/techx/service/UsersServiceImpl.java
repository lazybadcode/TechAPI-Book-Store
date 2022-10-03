package com.tech.book.store.techx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.tech.book.store.techx.controller.request.UsersRequest;
import com.tech.book.store.techx.exception.UserException;
import com.tech.book.store.techx.model.Orders;
import com.tech.book.store.techx.model.UserResponse;
import com.tech.book.store.techx.model.Users;
import com.tech.book.store.techx.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public Users createNewUser(UsersRequest users) {
		Users user = usersRepository.findByUsername(users.getUsername());
		// boolean IsUserExisitng =
		// usersRepository.existsByUsername(users.getUsername());
		if (user == null) {
			user = new Users().setUsername(users.getUsername())
					.setPassword(bCryptPasswordEncoder.encode(users.getPassword()))
					.setDate_of_birth(users.getDate_of_birth());
			return usersRepository.save(user);
		}
		throw new UserException(users.getUsername());
	}

	@Override
	public Users deleteUserByName(String userName) {
		Users user = usersRepository.findUserIdByName(userName);
		if (user != null) {

			usersRepository.deleteById(user.getId());

			return user;
		}
		throw UserException.notFound();
	}

	@Override
	public Users findByUsername(String userName) {

		return usersRepository.findUserIdByName(userName);
	}

	@Override
	public UserResponse getUserOrders(Users user) {
		List<Integer> idLists = new ArrayList<Integer>();

		for (Orders x : user.getOrders()) {
			idLists.add(x.getBookId());
		}

		List<Integer> orderList = idLists.stream().distinct().collect(Collectors.toList());

		UserResponse userResponse = new UserResponse(user.getUsername(), user.getPassword(), user.getDate_of_birth(),
				orderList);
		Gson gson = new Gson();
		String response = gson.toJson(userResponse);

		return gson.fromJson(response, UserResponse.class);
	}

	@Override
	public boolean matchPassword(String rawPassword, String encodedPassword) {
		return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
	}

}
