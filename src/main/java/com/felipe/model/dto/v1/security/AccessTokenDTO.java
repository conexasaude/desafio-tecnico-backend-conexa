package com.felipe.model.dto.v1.security;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe.util.MessageUtils;

import jakarta.validation.constraints.NotBlank;

public class AccessTokenDTO implements Serializable {


	private static final long serialVersionUID = 1L;

	@JsonProperty("token")
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	private String accessToken;

	public AccessTokenDTO() {
	}

	public AccessTokenDTO(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessToken);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		AccessTokenDTO other = (AccessTokenDTO) obj;
		return Objects.equals(accessToken, other.accessToken);
	}

	@Override
	public String toString() {
		return "TokenDTO [accessToken=" + accessToken + "]";
	}


}
