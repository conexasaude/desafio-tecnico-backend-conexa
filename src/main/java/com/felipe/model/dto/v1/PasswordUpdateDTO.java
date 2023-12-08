package com.felipe.model.dto.v1;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe.util.MessageUtils;

import jakarta.validation.constraints.AssertTrue;

public class PasswordUpdateDTO {
	
	@JsonProperty("senha")
	private String oldPassword;
	
	@JsonProperty("novaSenha")
	private String newPassword;
	
	@JsonProperty("confirmarNovaSenha")
	private String confirmNewPassword;
	
	public PasswordUpdateDTO() {
		super();
	}

	public PasswordUpdateDTO(String oldPassword, String newPassword, String confirmNewPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	
	@AssertTrue(message = MessageUtils.PASSWORD_MISMATCH)
	public boolean isPasswordConfirmed() {
		return newPassword.equals(confirmNewPassword);
	}

	@Override
	public int hashCode() {
		return Objects.hash(confirmNewPassword, newPassword, oldPassword);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordUpdateDTO other = (PasswordUpdateDTO) obj;
		return Objects.equals(confirmNewPassword, other.confirmNewPassword)
				&& Objects.equals(newPassword, other.newPassword) && Objects.equals(oldPassword, other.oldPassword);
	}

	
}
