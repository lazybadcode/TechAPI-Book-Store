package com.tech.book.store.techx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.tech.book.store.techx.model.Book;


public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query(value = "select * from book order by is_recommended desc;", nativeQuery = true)
	List<Book> GetAllBookOrderByRecommended();
	
	
	@Query(value = "sselect distinct r.book_id from orders r where r.user_id = :user_id;", nativeQuery = true)
	List<Book> GetAllBookByUser(long user_id);
	
}
