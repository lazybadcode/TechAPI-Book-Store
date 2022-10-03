package com.tech.book.store.techx.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.book.store.techx.model.Book;
import com.tech.book.store.techx.repository.BookRepository;


@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public List<Book> GetAllBookOrderByRecommended() {
		RestTemplate restTemplete = new RestTemplate();

		ResponseEntity<Object[]> book_responseEntity = restTemplete
				.getForEntity("https://scb-test-book-publisher.herokuapp.com/books", Object[].class);

		ResponseEntity<Object[]> book_recommend_responseEntity = restTemplete
				.getForEntity("https://scb-test-book-publisher.herokuapp.com/books/recommendation", Object[].class);

		Object[] book_obj = book_responseEntity.getBody();

		Object[] book_recommend_obj = book_recommend_responseEntity.getBody();

		ObjectMapper mapper = new ObjectMapper();

		List<Book> bookList = Arrays.stream(book_obj).map(object -> mapper.convertValue(object, Book.class))
				.collect(Collectors.toList());

		List<Integer> bookRecommendListIds = Arrays.stream(book_recommend_obj)
				.map(object -> mapper.convertValue(object, Book.class))
				.map(Book::getId)
				.collect(Collectors.toList());

		for (Book obj : bookList) {
			// find id of book recommended
			if(bookRecommendListIds.contains(obj.getId()) ) {
				obj.set_recommended(true);
			}
			
			bookRepository.save(obj);
	    }
		
		return bookRepository.GetAllBookOrderByRecommended();
	}

	@Override
	public List<Book> GetAllBookByUser(long userId) {
		
		return bookRepository.GetAllBookByUser(userId);
	}


}
