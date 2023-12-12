package com.felipe.model.dto.v1;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.felipe.util.MessageUtils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonPropertyOrder({ "id", "email", "account_non_expired", "account_non_locked", "credentials_non_expired", "enabled", "confirmed_email" })
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {
	private static final long serialVersionUID = 1L;


	
	@JsonProperty("id")
	private UUID key;
	
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@Email(message = "invalid E-mail")
	@JsonProperty("email")
	private String userName;
	
	@JsonProperty("account_non_expired")
	private Boolean accountNonExpired;

	@JsonProperty("account_non_locked")
	private Boolean accountNonLocked;

	@JsonProperty("credentials_non_expired")
	private Boolean credentialsNonExpired;

	@JsonProperty("enabled")
	private Boolean enabled;
	
	@JsonProperty("confirmed_email")
	private Boolean confirmedEmail;
    
	public UserDTO() {
	}

	public UUID getKey() {
		return key;
	}

	public void setKey(UUID key) {
		this.key = key;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getConfirmedEmail() {
		return confirmedEmail;
	}

	public void setConfirmedEmail(Boolean confirmedEmail) {
		this.confirmedEmail = confirmedEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(accountNonExpired, accountNonLocked, confirmedEmail,
				credentialsNonExpired, enabled, key, userName);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(accountNonExpired, other.accountNonExpired)
				&& Objects.equals(accountNonLocked, other.accountNonLocked)
				&& Objects.equals(confirmedEmail, other.confirmedEmail)
				&& Objects.equals(credentialsNonExpired, other.credentialsNonExpired)
				&& Objects.equals(enabled, other.enabled) && Objects.equals(key, other.key)
				&& Objects.equals(userName, other.userName);
	}

	@Override
	public String toString() {
		return "UserDTO [key=" + key + ", userName=" + userName + ", accountNonExpired=" + accountNonExpired
				+ ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired=" + credentialsNonExpired
				+ ", enabled=" + enabled + ", confirmedEmail=" + confirmedEmail + "]";
	}

}
