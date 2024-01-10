package com.felipe.exceptions;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExceptionResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private Date timestamp;
	private String message;
	private String details;
	private List<String> errors;

	public ExceptionResponse(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}


}
