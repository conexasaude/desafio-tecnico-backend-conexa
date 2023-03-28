package com.conexa.saude.model.entity;

import java.io.Serializable;

public class JwtResponseEntity implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public JwtResponseEntity(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}

}