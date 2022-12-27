package br.com.conexa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DifferentPasswordsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DifferentPasswordsException() {

		super("You must type the same password twice!");

	}

	public DifferentPasswordsException(String ex) {

		super(ex);

	}

}
