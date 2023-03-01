package com.satz.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// ResponseStatus annotation cause spring boot to respond with the 
// specified HTTP status code whenever this exception is thrown from your computer.

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private String resourceName;
	private String fieldName;
	private long fieldValue;
	
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : '%s'",resourceName,fieldName,fieldValue));
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

	public long getFieldValue() {
		return fieldValue;
	}

	
	
	
}
