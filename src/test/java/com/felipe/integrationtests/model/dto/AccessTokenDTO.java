package com.felipe.integrationtests.model.dto;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.xml.bind.annotation.XmlRootElement;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@XmlRootElement
public class AccessTokenDTO implements Serializable {


	private static final long serialVersionUID = 1L;

	private String token;
	
	public AccessTokenDTO() {
	}

	public AccessTokenDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		return Objects.hash(token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessTokenDTO other = (AccessTokenDTO) obj;
		return Objects.equals(token, other.token);
	}

	@Override
	public String toString() {
		return "AccessTokenDTO [token=" + token + "]";
	}




}
