package com.felipe.model.dto.v1.security;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogoutDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("access_token")
	private String acessToken;

	public LogoutDTO() {
	}

	public LogoutDTO(String acessToken) {
		this.acessToken = acessToken;
	}

	public String getAcessToken() {
		return acessToken;
	}

	public void setAcessToken(String acessToken) {
		this.acessToken = acessToken;
	}

	@Override
	public int hashCode() {
		return Objects.hash(acessToken);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogoutDTO other = (LogoutDTO) obj;
		return Objects.equals(acessToken, other.acessToken);
	}

	@Override
	public String toString() {
		return "LogoutDTO [acessToken=" + acessToken + "]";
	}

}
