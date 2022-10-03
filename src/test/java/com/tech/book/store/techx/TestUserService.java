package com.tech.book.store.techx;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tech.book.store.techx.controller.request.UserAuthenRequest;
import com.tech.book.store.techx.controller.request.UsersRequest;
import com.tech.book.store.techx.exception.UserException;
import com.tech.book.store.techx.model.Orders;
import com.tech.book.store.techx.model.UserAuthen;
import com.tech.book.store.techx.model.Users;
import com.tech.book.store.techx.service.OrdersService;
import com.tech.book.store.techx.service.UserAuthenService;
import com.tech.book.store.techx.service.UsersService;

@SpringBootTest
public class TestUserService {
	@Autowired
	private UsersService usersService;
	private OrdersService ordersService;
	private UserAuthenService userAuthenService;
	
	
	@Order(1)
	@Test
	void testRegisterUser() {
		UserAuthenRequest user = new UserAuthenRequest(TestUserData.username, TestUserData.password, TestUserData.role);
		UserAuthen userAuthen =  userAuthenService.register(user);
		
		// check not null
        Assertions.assertNotNull(userAuthen);
        Assertions.assertNotNull(userAuthen.getUsername());

        // check equals
        Assertions.assertEquals(TestUserData.username, userAuthen.getUsername());
        
        boolean isMatched = usersService.matchPassword(TestUserData.password, userAuthen.getPassword());
        Assertions.assertTrue(isMatched);
        
        Assertions.assertEquals(TestUserData.role, userAuthen.getRole());
        
        
	}
	
	@Order(2)
	@Test
	void testLoginUser() {
		
		UserAuthen userAuthen =  userAuthenService.findUserByUsername(TestUserData.username);
		
		// check not null
        Assertions.assertNotNull(userAuthen);
	}
	
	
	@Order(3)
	@Test
	void testCreateUser() throws UserException {
		UsersRequest user = new UsersRequest(TestUserData.username, TestUserData.password, TestUserData.date_of_birth);
		usersService.createNewUser(user);
		
		// check not null
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getUsername());

        // check equals
        Assertions.assertEquals(TestUserData.username, user.getUsername());
        
        boolean isMatched = usersService.matchPassword(TestUserData.password, user.getPassword());
        Assertions.assertTrue(isMatched);
        
        Assertions.assertEquals(TestUserData.date_of_birth, user.getDate_of_birth());
	}
	
	@Order(4)
    @Test
    void testCreateOrder() {
		Optional<Users> opt = Optional.ofNullable(usersService.findByUsername(TestUserData.username));
        Assertions.assertTrue(opt.isPresent());
        
        Users user = opt.get();
        
        List<Orders> orders = user.getOrders();
        Assertions.assertTrue(orders.isEmpty());
        
        //create order
        createOrder(user, 1);
        createOrder(user ,2);
    }
	
	@Order(5)
	@Test
	void testDeleteUser() {
		// find user by Username
		Optional<Users> opt = Optional.ofNullable(usersService.findByUsername(TestUserData.username));
        Assertions.assertTrue(opt.isPresent());

        Users user = opt.get();
		
        //delete user
        Optional<Users> optDelete =  Optional.ofNullable(usersService.deleteUserByName(user.getUsername()));
		
		Assertions.assertTrue(optDelete.isEmpty());
		
	}
	
	private void createOrder(Users user, int bookId) {
        
		Orders order = ordersService.createOrders(user, bookId);
		
        Assertions.assertNotNull(order);
        Assertions.assertEquals(bookId, order.getBookId());
        Assertions.assertEquals(user.getUsername(), order.getUser().getUsername());
   
    }
	
	
	interface TestUserData{
		 String username = "userwinneyTest9999";
		 String password = "12345678";
		 String date_of_birth = "15/01/1985";
		 String role = "Admin";
	}
}
