package com.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends Exception{

	private static final long serialVersionUID = 1L;

	private HttpStatus badRequest;
	private String message;

	public BlogAPIException(HttpStatus badRequest, String message) {
		
		super(String.format("%s not found with %s:'%s",badRequest,message));
		this.badRequest = badRequest;
		this.message = message;

	}

	public HttpStatus getBadRequest() {
		return badRequest;
	}

	public void setBadRequest(HttpStatus badRequest) {
		this.badRequest = badRequest;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
