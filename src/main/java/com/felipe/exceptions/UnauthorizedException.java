package com.felipe.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String ex) {
		super(ex);
	}
}
