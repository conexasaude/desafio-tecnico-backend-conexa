package com.felipe.model.dto.v1;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "email", "full_name", "specialty", "cpf", "birth_date", "phone" })
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private UUID key;
	
	private String email;
	
	@JsonProperty("full_name")
	private String fullName;
	
	@JsonIgnore
	private String password;
	
	private String specialty;
	
	private String cpf;
	
	@JsonProperty("birth_date")
	private LocalDate birthDate;
	
	private String phone;
	
    public UserDTO() {
    }
    
	public UserDTO(UUID key, String email, String fullName, String password, String specialty, String cpf,
			LocalDate birthDate, String phone) {
		this.key = key;
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.specialty = specialty;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.phone = phone;
	}
	
	public UUID getKey() {
		return key;
	}

	public void setKey(UUID key) {
		this.key = key;
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

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(birthDate, cpf, email, fullName, key, password, phone, specialty);
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
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(email, other.email) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(key, other.key) && Objects.equals(password, other.password)
				&& Objects.equals(phone, other.phone) && Objects.equals(specialty, other.specialty);
	}

}
