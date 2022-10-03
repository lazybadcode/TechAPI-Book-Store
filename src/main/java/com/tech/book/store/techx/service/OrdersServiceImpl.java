package com.tech.book.store.techx.service;


import java.util.Date;

import org.springframework.stereotype.Service;

import com.tech.book.store.techx.exception.UserException;
import com.tech.book.store.techx.model.Orders;
import com.tech.book.store.techx.model.Users;
import com.tech.book.store.techx.repository.OrdersRepository;


@Service
public class OrdersServiceImpl implements OrdersService {

	private final OrdersRepository ordeRepository;

	public OrdersServiceImpl(OrdersRepository ordeRepository) {
		this.ordeRepository = ordeRepository;
	}

	@Override
	public Orders createOrders(Users user, int bookId) {
		
		if(user != null) {
			// create
	        Orders order = new Orders();
	        order.setUser(user);
	        order.setBookId(bookId);
	        order.setOrderDate(new Date());
	        return ordeRepository.save(order);
		}
		throw UserException.notFound();
 
	}

	@Override
	public double getPriceOfBooks(long userId) {
		
		return ordeRepository.getPriceOfBooks(userId);
	}

	
		
}
