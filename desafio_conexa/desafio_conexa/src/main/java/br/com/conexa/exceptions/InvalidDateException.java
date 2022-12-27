package br.com.conexa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidDateException extends AuthenticationException {

	private static final long serialVersionUID = 1L;
	
	public InvalidDateException(String ex) {

		super(ex);

	}
	
	public InvalidDateException() {
		
		super("Invalid date exception!");
		
	}

}
