package com.tech.book.store.techx.service;

import com.tech.book.store.techx.controller.request.UsersRequest;
import com.tech.book.store.techx.model.UserResponse;
import com.tech.book.store.techx.model.Users;

public interface UsersService {
	Users createNewUser(UsersRequest users);
	
	Users deleteUserByName(String userName);
	
	Users findByUsername(String userName);
	
	UserResponse getUserOrders(Users user);
	
	boolean matchPassword(String rawPassword, String encodedPassword);

}
