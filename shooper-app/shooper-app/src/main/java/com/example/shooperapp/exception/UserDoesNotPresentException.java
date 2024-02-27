package com.example.shooperapp.exception;

public class UserDoesNotPresentException extends RuntimeException {

	private String message="Id is not present";
	
	public UserDoesNotPresentException() {
		
	}
	
	public UserDoesNotPresentException(String message) {
		this.message=message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	
}
