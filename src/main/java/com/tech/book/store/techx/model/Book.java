package com.tech.book.store.techx.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "`book`")
public class Book {
	
	@Id
	@Column(nullable = false)
	private int id;

	@Column(nullable = false)
	private String book_name;

	@Column(nullable = false)
	private String author_name;

	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private boolean is_recommended;
}
