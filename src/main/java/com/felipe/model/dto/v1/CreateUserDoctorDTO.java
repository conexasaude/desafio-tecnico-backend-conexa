package com.felipe.model.dto.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe.util.MessageUtils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateUserDoctorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@Email(message = "invalid E-mail")
	private String email;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@JsonProperty("nomeCompleto")
	private String fullName;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@JsonProperty("senha")
	private String password;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@JsonProperty("confirmacaoSenha")
	private String confirmPassword;

	@JsonProperty("especialidade")
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	private String specialty;

	
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@Size(min = 11, max = 14, message = "CPF must be between 11 and 14 characters")
	private String cpf;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@JsonProperty("dataNascimento")
	private String birthDate;

	/**
	 * Formats:
	 * (XX) XXXX-XXXX ; (XX) XXXXX-XXXX ; 11XXXXXXXX ; 11XXXXXXXXX
	 */
	@JsonProperty("telefone")
	@Pattern(regexp = "^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$|^\\d{11}$", message = "Invalid phone number format")
	private String phone;

	public CreateUserDoctorDTO() {
	}

	public CreateUserDoctorDTO(String email, String fullName, String password, String confirmPassword, String cpf,
			String birthDate, String phone, String specialty) {
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.phone = phone;
		this.specialty = specialty;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

    @Valid
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@AssertTrue(message = MessageUtils.PASSWORD_MISMATCH)
	public boolean isPasswordConfirmed() {
		return password.equals(confirmPassword);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(birthDate, confirmPassword, cpf, email, fullName, password, phone, specialty);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateUserDoctorDTO other = (CreateUserDoctorDTO) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(confirmPassword, other.confirmPassword)
				&& Objects.equals(cpf, other.cpf) && Objects.equals(email, other.email)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(password, other.password)
				&& Objects.equals(phone, other.phone) && Objects.equals(specialty, other.specialty);
	}

}
