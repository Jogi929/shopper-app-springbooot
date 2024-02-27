package com.example.shooperapp.exception;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class InvalidActionException extends RuntimeException {

	private String message = "Action is not valid to perform";

	public InvalidActionException() {

	}

	public InvalidActionException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
