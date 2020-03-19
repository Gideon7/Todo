package com.todoapplication.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6545798126864628092L;
	
	public AuthenticationException(String message, Throwable cause) {
		super(message,cause);
	}
	
}
