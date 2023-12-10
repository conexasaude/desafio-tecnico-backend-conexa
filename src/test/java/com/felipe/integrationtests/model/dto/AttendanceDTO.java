package com.felipe.integrationtests.model.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.felipe.model.validation.FutureDateTime;
import com.felipe.util.MessageUtils;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AttendanceDTO extends RepresentationModel<AttendanceDTO> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private UUID id;

	@NotBlank(message = MessageUtils.CANNOT_BLANK)
	@FutureDateTime(message = "The date and time must be in the future")
	private String dateTime;


	@NonNull
	private PatientDTO patient;

	public AttendanceDTO() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	
	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(dateTime, id, patient);
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
		AttendanceDTO other = (AttendanceDTO) obj;
		return Objects.equals(dateTime, other.dateTime) && Objects.equals(id, other.id)
				&& Objects.equals(patient, other.patient);
	}

	@Override
	public String toString() {
		return "AttendanceDTO [id=" + id + ", dateTime=" + dateTime + ", patient=" + patient + "]";
	}

}
