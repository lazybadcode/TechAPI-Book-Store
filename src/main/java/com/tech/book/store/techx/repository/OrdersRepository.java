package com.tech.book.store.techx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tech.book.store.techx.model.Orders;
import com.tech.book.store.techx.model.Users;

public interface OrdersRepository extends JpaRepository<Orders, Long>{
	List<Orders> findByUser(Users user);
	
	@Query(value = "select sum(price) as price from book b where b.id in (\r\n"
			+ "	select r.book_id from orders r where r.user_id = :user_id\r\n"
			+ ");", nativeQuery = true)
	double getPriceOfBooks(long user_id);
	
}
