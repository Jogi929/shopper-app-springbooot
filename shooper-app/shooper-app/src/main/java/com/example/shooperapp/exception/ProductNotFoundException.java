package com.example.shooperapp.exception;

public class ProductNotFoundException extends RuntimeException {
	private String message = "Product is not found in database.";

	public ProductNotFoundException() {

	}

	public ProductNotFoundException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
