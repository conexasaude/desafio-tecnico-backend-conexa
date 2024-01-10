package com.felipe.integrationtests.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private Boolean authenticated;
	private Date created;
	private Date expiration;

	@JsonProperty("acess_token")
	private String acessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	public TokenDTO() {
	}

	public TokenDTO(String email, Boolean authenticated, Date created, Date expiration, String acessToken,
			String refreshToken) {
		this.email = email;
		this.authenticated = authenticated;
		this.created = created;
		this.expiration = expiration;
		this.acessToken = acessToken;
		this.refreshToken = refreshToken;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public String getAcessToken() {
		return acessToken;
	}

	public void setAcessToken(String acessToken) {
		this.acessToken = acessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public int hashCode() {
		return Objects.hash(acessToken, authenticated, created, expiration, refreshToken, email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		TokenDTO other = (TokenDTO) obj;
		return Objects.equals(acessToken, other.acessToken) && Objects.equals(authenticated, other.authenticated)
				&& Objects.equals(created, other.created) && Objects.equals(expiration, other.expiration)
				&& Objects.equals(refreshToken, other.refreshToken) && Objects.equals(email, other.email);
	}

}
