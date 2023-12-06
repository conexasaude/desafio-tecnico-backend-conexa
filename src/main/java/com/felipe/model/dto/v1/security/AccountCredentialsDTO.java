package com.felipe.model.dto.v1.security;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String password;

	public AccountCredentialsDTO() {
	}

	public AccountCredentialsDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountCredentialsDTO other = (AccountCredentialsDTO) obj;
		return Objects.equals(password, other.password) && Objects.equals(email, other.email);
	}

	
}
