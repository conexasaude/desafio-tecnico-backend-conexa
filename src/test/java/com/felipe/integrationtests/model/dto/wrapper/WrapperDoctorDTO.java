package com.felipe.integrationtests.model.dto.wrapper;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperDoctorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("_embedded")
	private DoctorEmbeddedDTO embeddedDTO;

	public WrapperDoctorDTO() {
	}

	public DoctorEmbeddedDTO getEmbeddedDTO() {
		return embeddedDTO;
	}

	public void setEmbeddedDTO(DoctorEmbeddedDTO embeddedDTO) {
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
		WrapperDoctorDTO other = (WrapperDoctorDTO) obj;
		return Objects.equals(embeddedDTO, other.embeddedDTO);
	}

	@Override
	public String toString() {
		return "WrapperDoctorDTO [embeddedDTO=" + embeddedDTO + "]";
	}
	
	
	
}
