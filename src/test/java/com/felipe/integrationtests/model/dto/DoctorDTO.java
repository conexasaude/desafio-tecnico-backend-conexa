package com.felipe.integrationtests.model.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DoctorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID id;
	private String email;
	private String fullName;
	private String password;
	private String cpf;
	private String birthDate;
	private String phone;
	private String specialty;

	public DoctorDTO() {
	}

	public DoctorDTO(String email, String fullName, String password, String cpf, String birthDate, String phone,
			String specialty) {
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.phone = phone;
		this.specialty = specialty;
	}

	public DoctorDTO(UUID id, String email, String fullName, String password, String cpf, String birthDate,
			String phone, String specialty) {
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.phone = phone;
		this.specialty = specialty;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return Objects.hash(birthDate, cpf, email, fullName, id, password, phone, specialty);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		DoctorDTO other = (DoctorDTO) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(email, other.email)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(id, other.id)
				&& Objects.equals(password, other.password) && Objects.equals(phone, other.phone)
				&& Objects.equals(specialty, other.specialty);
	}

	@Override
	public String toString() {
		return "DoctorDTO [id=" + id + ", email=" + email + ", fullName=" + fullName + ", password=" + password
				+ ", cpf=" + cpf + ", birthDate=" + birthDate + ", phone=" + phone + ", specialty=" + specialty + "]";
	}



}
