package com.felipe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbbidenException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public ForbbidenException(String ex) {
		super(ex);
	}
}
