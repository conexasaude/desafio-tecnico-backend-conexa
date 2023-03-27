package com.conexa.saude.excepetions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
    private String message;

    public BadRequestException(String message) {
		super();
		this.message = message;
	}

}
