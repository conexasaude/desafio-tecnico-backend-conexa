package com.conexa.saude.excepetions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public UnauthorizedException(String message) {
		super();
		this.message = message;
	}
}
