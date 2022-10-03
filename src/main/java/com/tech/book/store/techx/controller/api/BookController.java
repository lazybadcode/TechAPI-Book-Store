package com.tech.book.store.techx.controller.api;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tech.book.store.techx.model.Book;
import com.tech.book.store.techx.service.BookService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "techxapi")
public class BookController {
	
	private final BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping(
			  value = "/books", 
			  method = RequestMethod.GET, 
			  produces = "application/json"
			)
	@ResponseBody
	public Map<String, List<Book>> getAllBooks() {
		Map<String, List<Book>> responseJSON = new HashMap<String, List<Book>>();
		responseJSON.put("books", bookService.GetAllBookOrderByRecommended());
		return responseJSON;
	}
	
}
