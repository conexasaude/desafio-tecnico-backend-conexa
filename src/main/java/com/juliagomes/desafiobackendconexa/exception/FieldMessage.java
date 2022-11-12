package com.juliagomes.desafiobackendconexa.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String campo;
	private String message;

	public FieldMessage() {
		super();
	}

	public FieldMessage(String campo, String message) {
		super();
		this.campo = campo;
		this.message = message;
	}

	public String getcampo() {
		return campo;
	}

	public void setcampo(String campo) {
		this.campo = campo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
