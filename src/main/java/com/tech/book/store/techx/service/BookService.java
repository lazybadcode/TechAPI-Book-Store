package com.tech.book.store.techx.service;

import java.util.List;
import java.util.Optional;

import com.tech.book.store.techx.model.Book;

public interface BookService {
	
	List<Book> GetAllBookOrderByRecommended();
	
	List<Book> GetAllBookByUser(long userId);
}
