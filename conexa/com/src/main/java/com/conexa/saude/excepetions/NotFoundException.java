package com.conexa.saude.excepetions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
    
    private final String message;
	
    public NotFoundException(String message) {
		super();
		this.message = message;
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
