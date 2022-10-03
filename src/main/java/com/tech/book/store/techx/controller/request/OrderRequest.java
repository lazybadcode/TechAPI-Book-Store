package com.tech.book.store.techx.controller.request;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Service
@NoArgsConstructor
public class OrderRequest {
	@NotEmpty
	private String[] orders;

}
