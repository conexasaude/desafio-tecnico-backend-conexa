package com.felipe.integrationtests.model.dto.wrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe.integrationtests.model.dto.DoctorDTO;

public class DoctorEmbeddedDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("doctor_dtolist")
	private List<DoctorDTO> dtos;

	public DoctorEmbeddedDTO() {
	}

	public List<DoctorDTO> getDtos() {
		return dtos;
	}

	public void setDtos(List<DoctorDTO> dtos) {
		this.dtos = dtos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dtos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoctorEmbeddedDTO other = (DoctorEmbeddedDTO) obj;
		return Objects.equals(dtos, other.dtos);
	}

	@Override
	public String toString() {
		return "DoctorEmbeddedDTO [dtos=" + dtos + "]";
	}

}
