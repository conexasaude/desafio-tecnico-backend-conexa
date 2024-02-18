package com.felipe.model.dto.v1;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe.util.MessageUtils;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateUserDoctorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@Email(message = "invalid E-mail")
	private String email;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@JsonProperty("full_name")
	private String fullName;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	private String password;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@JsonProperty("confirm_password")
	private String confirmPassword;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	private String specialty;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@CPF(message = "CPF invalid")
	private String cpf;

	@JsonProperty("birth_date")
	private LocalDate birthDate;

	/**
	 * Formats:
	 * (XX) XXXX-XXXX ; (XX) XXXXX-XXXX ; 11XXXXXXXX ; 11XXXXXXXXX
	 */
	@Pattern(regexp = "^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$|^\\d{11}$", message = "Invalid phone number format")
	private String phone;

	public CreateUserDoctorDTO() {
	}

	public CreateUserDoctorDTO(String email, String fullName, String password, String confirmPassword, String cpf,
			LocalDate birthDate, String phone, String specialty) {
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
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
	@JsonIgnore
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
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		CreateUserDoctorDTO other = (CreateUserDoctorDTO) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(confirmPassword, other.confirmPassword)
				&& Objects.equals(cpf, other.cpf) && Objects.equals(email, other.email)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(password, other.password)
				&& Objects.equals(phone, other.phone) && Objects.equals(specialty, other.specialty);
	}

}
