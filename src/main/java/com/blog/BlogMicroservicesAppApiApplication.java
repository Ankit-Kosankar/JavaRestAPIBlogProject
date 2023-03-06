package com.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogMicroservicesAppApiApplication {

	//bean of ModelMapper mapper cannot be created automatically as it 
	//modelMapper is an external Library
	//constructor based injection wont work as spring container dont know what it is
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BlogMicroservicesAppApiApplication.class, args);
	}
}
