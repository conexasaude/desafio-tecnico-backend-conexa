package com.felipe.model.dto.v1.security;

import java.io.Serializable;
import java.util.Objects;

import com.felipe.util.MessageUtils;

import jakarta.validation.constraints.NotBlank;

public class AccountCredentialsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	private String email;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
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
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		AccountCredentialsDTO other = (AccountCredentialsDTO) obj;
		return Objects.equals(password, other.password) && Objects.equals(email, other.email);
	}


}
