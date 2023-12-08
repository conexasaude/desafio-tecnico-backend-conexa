package com.felipe.model.dto.v1;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.felipe.util.MessageUtils;

import jakarta.validation.constraints.NotBlank;

@JsonPropertyOrder({ "id", "nome", "cpf", "planoDeSaude" })
public class PatientDTO extends RepresentationModel<PatientDTO> implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private UUID key;
	
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@JsonProperty("nome")
	private String fullName;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@CPF(message = "CPF invalid")
	private String cpf;

	@JsonProperty("planoDeSaude")
	private String healthInsurance;

//	@CreatedDate
	@JsonIgnore
	private LocalDateTime createdAt;

//	@LastModifiedDate
	@JsonIgnore
	private LocalDateTime updatedAt;

	public PatientDTO() {
	}

	public PatientDTO(String fullName, String cpf, String healthInsurance) {
		this.fullName = fullName;
		this.cpf = cpf;
		this.healthInsurance = healthInsurance;
	}

	public PatientDTO(UUID key, String fullName, String cpf, String healthInsurance) {
		this.key = key;
		this.fullName = fullName;
		this.cpf = cpf;
		this.healthInsurance = healthInsurance;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getHealthInsurance() {
		return healthInsurance;
	}

	public void setHealthInsurance(String healthInsurance) {
		this.healthInsurance = healthInsurance;
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
		result = prime * result + Objects.hash(cpf, createdAt, fullName, healthInsurance, key, updatedAt);
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
		PatientDTO other = (PatientDTO) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(healthInsurance, other.healthInsurance)
				&& Objects.equals(key, other.key) && Objects.equals(updatedAt, other.updatedAt);
	}

	@Override
	public String toString() {
		return "PatientDTO [key=" + key + ", fullName=" + fullName + ", cpf=" + cpf + ", healthInsurance="
				+ healthInsurance + "]";
	}

}
