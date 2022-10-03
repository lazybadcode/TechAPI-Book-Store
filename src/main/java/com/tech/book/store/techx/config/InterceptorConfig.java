package com.tech.book.store.techx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tech.book.store.techx.interceptor.GeneralInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	
	@Autowired
	private GeneralInterceptor generalInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(generalInterceptor);
	}
	
}
