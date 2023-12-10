package com.felipe.integrationtests.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.felipe.model.Attendance;
import com.felipe.util.MessageUtils;

import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PatientDTO extends RepresentationModel<PatientDTO> implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private UUID id;
	
	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@JsonProperty("full_name")
	private String fullName;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@CPF(message = "CPF invalid")
	private String cpf;

	@JsonProperty("health_insurance")
	private String healthInsurance;

	private List<Attendance> attendances;

	public PatientDTO() {
	}

	public PatientDTO(@NotBlank(message = "This value cannot be blank or null") String fullName,
			@NotBlank(message = "This value cannot be blank or null") @CPF(message = "CPF invalid") String cpf,
			String healthInsurance, List<Attendance> attendances) {
		this.fullName = fullName;
		this.cpf = cpf;
		this.healthInsurance = healthInsurance;
		this.attendances = attendances;
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

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(attendances, cpf, fullName, healthInsurance, id);
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
		return Objects.equals(attendances, other.attendances) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(fullName, other.fullName)
				&& Objects.equals(healthInsurance, other.healthInsurance) && Objects.equals(id, other.id);

	}

	@Override
	public String toString() {
		return "PatientDTO [id=" + id + ", fullName=" + fullName + ", cpf=" + cpf + ", healthInsurance="
				+ healthInsurance + ", attendances=" + attendances + "]";
	}



}
