package com.conexa.saude.excepetions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public NotFoundException(String message) {
		super();
		this.message = message;
	}
}
