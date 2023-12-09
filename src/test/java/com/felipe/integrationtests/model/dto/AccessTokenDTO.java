package com.felipe.integrationtests.model.dto;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe.util.MessageUtils;

import jakarta.validation.constraints.NotBlank;

public class AccessTokenDTO implements Serializable {


	private static final long serialVersionUID = 1L;

	@JsonProperty("token")
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	private String acessToken;
	
	public AccessTokenDTO() {
	}
	
	public AccessTokenDTO(String acessToken) {
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
		AccessTokenDTO other = (AccessTokenDTO) obj;
		return Objects.equals(acessToken, other.acessToken);
	}

	@Override
	public String toString() {
		return "TokenDTO [acessToken=" + acessToken + "]";
	}


}
