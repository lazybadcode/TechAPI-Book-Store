package com.tech.book.store.techx.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tech.book.store.techx.controller.request.OrderRequest;
import com.tech.book.store.techx.controller.request.UserLogin;
import com.tech.book.store.techx.controller.request.UsersRequest;
import com.tech.book.store.techx.controller.request.UserAuthenRequest;
import com.tech.book.store.techx.model.Orders;
import com.tech.book.store.techx.model.UserAuthen;
import com.tech.book.store.techx.model.UserResponse;
import com.tech.book.store.techx.model.Users;
import com.tech.book.store.techx.service.OrdersService;
import com.tech.book.store.techx.service.UserAuthenService;
import com.tech.book.store.techx.service.UsersService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.security.core.context.SecurityContextHolder;
import java.text.DecimalFormat;
import com.google.gson.Gson;

@RequestMapping("/users")
@RestController
@SecurityRequirement(name = "techxapi")
public class UserController {

	private final UserAuthenService userAuthenService;
	private final UsersService userService;
	private final OrdersService ordersService;
	private static final DecimalFormat df = new DecimalFormat("0.00");
	public UserController(UserAuthenService userAuthenService,UsersService userService, OrdersService ordersService) {
		this.userAuthenService = userAuthenService;
		this.userService = userService;
		this.ordersService = ordersService;
	}

	@PostMapping("/register")
	public UserAuthen register(@Valid @RequestBody UserAuthenRequest userRequest, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ValidationException(fieldError.getField() + ": " + fieldError.getDefaultMessage());
			});
		}

		return userAuthenService.register(userRequest);
	}

	@PostMapping("/login")
	public UserAuthen login(@Valid @RequestBody UserLogin userLogin, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ValidationException(fieldError.getField() + ": " + fieldError.getDefaultMessage());
			});
		}

		return userAuthenService.findUserByUsername(userLogin.getUsername());
	}
	
	@GetMapping("/getUserOrder")
	@ResponseBody
	public UserResponse getUser() {
		
		Object userobj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Users user = userService.findByUsername(userobj.toString());
		
		return userService.getUserOrders(user);
		
	}
	
	@PostMapping("/create")
	public Users create(@Valid @RequestBody UsersRequest users, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ValidationException(fieldError.getField() + ": " + fieldError.getDefaultMessage());
			});
		}
		
		return userService.createNewUser(users);

	}
	
	@DeleteMapping("/delete")
	@ResponseBody
	public Map<String, Users> delete() {
		Object userobj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Map<String, Users> responseJSON = new HashMap<String, Users>();
		responseJSON.put("This user has been deleted", userService.deleteUserByName(userobj.toString()));
		return responseJSON;
		
	}
	
	@PostMapping("/orders")
	public Map<String, String> order(@Valid @RequestBody OrderRequest orderRequest, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ValidationException(fieldError.getField() + ": " + fieldError.getDefaultMessage());
			});
		}
		Object userobj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Users user = userService.findByUsername(userobj.toString());
		
		for (String order: orderRequest.getOrders()) { 
			ordersService.createOrders(user, Integer.parseInt(order));
		}
		
		double price = ordersService.getPriceOfBooks(user.getId());
		Map<String, String> responseJSON = new HashMap<String, String>();
		responseJSON.put("Price", df.format(price));
		return responseJSON;
		
	}


}
