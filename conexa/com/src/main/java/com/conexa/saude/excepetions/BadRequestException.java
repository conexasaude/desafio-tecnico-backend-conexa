package com.conexa.saude.excepetions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="No such Order")
public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
    private final String message;
    

    public BadRequestException(String message) {
		super();
		this.message = message;
	}

    @Override
	public String getMessage() {
		return message;
	}
	

}
