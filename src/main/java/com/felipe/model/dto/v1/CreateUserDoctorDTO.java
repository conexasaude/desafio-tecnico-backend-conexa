package com.felipe.model.dto.v1.security;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserDoctorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	@JsonProperty("full_name")
	private String fullName;

	private String password;

	@JsonProperty("confirm_password")
	private String confirmPassword;

	private String cpf;

	@JsonProperty("birth_date")
	private String birthDate;

	private String phone;

	private String specialty;

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
