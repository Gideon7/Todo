package com.todoapplication.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidTokenRequestFilterException  extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8514785273209525132L;
	private final String tokenType;
	    private final String token;
	    private final String message;

	    public InvalidTokenRequestFilterException(String tokenType, String token, String message) {
	        super(String.format("%s: [%s] token: [%s] ", message, tokenType, token));
	        this.tokenType = tokenType;
	        this.token = token;
	        this.message = message;
	    }

}
