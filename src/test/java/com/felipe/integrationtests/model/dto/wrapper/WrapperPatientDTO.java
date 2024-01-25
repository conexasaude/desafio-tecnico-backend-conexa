package com.felipe.integrationtests.model.dto.wrapper;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperPatientDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("_embedded")
	private PatientEmbeddedDTO embeddedDTO;

	public WrapperPatientDTO() {
	}

	public PatientEmbeddedDTO getEmbeddedDTO() {
		return embeddedDTO;
	}

	public void setEmbeddedDTO(PatientEmbeddedDTO embeddedDTO) {
		this.embeddedDTO = embeddedDTO;
	}

	@Override
	public int hashCode() {
		return Objects.hash(embeddedDTO);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WrapperPatientDTO other = (WrapperPatientDTO) obj;
		return Objects.equals(embeddedDTO, other.embeddedDTO);
	}

	@Override
	public String toString() {
		return "WrapperPatientDTO [embeddedDTO=" + embeddedDTO + "]";
	}
	
	
	
}
