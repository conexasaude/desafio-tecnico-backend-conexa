package com.felipe.model.dto.v1;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.felipe.util.MessageUtils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonPropertyOrder({ "id", "email", "full_name", "specialty", "cpf", "birth_date", "phone" })
public class DoctorDTO extends RepresentationModel<DoctorDTO> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private UUID key;
	
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@Email(message = "invalid E-mail")
	private String email;
	
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@JsonProperty("full_name")
	private String fullName;
	
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@Size(min = 11, max = 14, message = "CPF must be between 11 and 14 characters")
	private String cpf;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@JsonProperty("dataNascimento")
	private String birthDate;
	
	@JsonProperty("telefone")
	@Pattern(regexp = "^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$|^\\d{11}$", message = "Invalid phone number format")
	private String phone;
	
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	private String specialty;

//	@JsonProperty("created_at")
	@JsonIgnore
	private LocalDateTime createdAt;

//	@JsonProperty("updated_at")
	@JsonIgnore
    private LocalDateTime updatedAt;
    
	public DoctorDTO() {
	}

	public DoctorDTO(String email, String fullName, String cpf, String birthDate, String phone,
			String specialty) {
		this.email = email;
		this.fullName = fullName;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.phone = phone;
		this.specialty = specialty;
	}

	public DoctorDTO(UUID key, String email, String fullName, String cpf, String birthDate,
			String phone, String specialty) {
		this.key = key;
		this.email = email;
		this.fullName = fullName;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.phone = phone;
		this.specialty = specialty;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ Objects.hash(birthDate, cpf, createdAt, email, fullName, key, phone, specialty, updatedAt);
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
		DoctorDTO other = (DoctorDTO) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(email, other.email)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(key, other.key)
				&& Objects.equals(phone, other.phone)
				&& Objects.equals(specialty, other.specialty) && Objects.equals(updatedAt, other.updatedAt);
	}

	@Override
	public String toString() {
		return "DoctorDTO [key=" + key + ", email=" + email + ", fullName=" + fullName + ", password="
				+ ", cpf=" + cpf + ", birthDate=" + birthDate + ", phone=" + phone + ", specialty=" + specialty
				+ "]";
	}
	

	
}
