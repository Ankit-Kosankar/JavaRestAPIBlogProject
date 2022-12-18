package com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	//tings that you want to display when something goes wrong
	//create Variable Acordingly step2
	private String resourceName;
	private String fieldName;
	private Long fieldValue;
	
	//step3 create a default Constructor
	public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
		//this super keyword is automatically called customized messege in super give any parameter
		super(String.format("%s not found with %s:'%s",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Long getFieldValue() {
		return fieldValue;
	}
	
	
	//Info whenever something is not found an object of this class will be created and 
	//COnstructor of this class will be called automatically 
	//return the constructor
	
	
}
