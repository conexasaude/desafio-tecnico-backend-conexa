package com.juliagomes.desafiobackendconexa.exception;

import java.io.Serializable;


public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long timestamp;
	private Integer starus;
	private String error;
	private String message;
	private String path;

	public StandardError() {
		super();
	}

	public StandardError(Long timestamp, Integer starus, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.starus = starus;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStarus() {
		return starus;
	}

	public void setStarus(Integer starus) {
		this.starus = starus;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
