package com.tech.book.store.techx.service;

import com.tech.book.store.techx.model.Orders;
import com.tech.book.store.techx.model.Users;

public interface OrdersService {
	
	Orders createOrders(Users user, int bookId);
	
	double getPriceOfBooks(long userId);
}
