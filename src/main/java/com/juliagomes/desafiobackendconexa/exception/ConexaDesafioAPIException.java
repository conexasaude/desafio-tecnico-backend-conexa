package com.juliagomes.desafiobackendconexa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConexaDesafioAPIException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	private String message;

	public ConexaDesafioAPIException() {
		super();
	}

	public ConexaDesafioAPIException(String message) {
		super(message);
	}

	public ConexaDesafioAPIException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {

		return message;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
