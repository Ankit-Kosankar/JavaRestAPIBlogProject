package com.blog.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.blog.payload.ErrorDetails;

//whenever exception occurs this annotation is checked first control goes to Controller Advice
//if any exception occour's spring boot will pass the exception here in this class
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	//Catch block to catch the specific Exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
		ResourceNotFoundException exception,
		WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(
				new Date(),
				exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.OK);
	}
	
	//GlobalExceptionHandler
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(
		Exception exception,
		WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(
				new Date(),
				exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}