package com.example.shooperapp.exception;

public class ReviewNotFoundException extends RuntimeException {
	private String message = "Review is not found for any product in the database.";

	public ReviewNotFoundException() {

	}

	public ReviewNotFoundException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
