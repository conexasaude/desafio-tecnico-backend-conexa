package br.com.conexa.vo;

import java.util.Date;

public class TokenVO {


	private String email;
	private Date created;
	private Date expiration;
	private String accessToken;

	public TokenVO(String email, Date created, Date expiration, String accessToken) {
		this.email = email;
		this.created = created;
		this.expiration = expiration;
		this.accessToken = accessToken;
	}

	public TokenVO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	
	

}
