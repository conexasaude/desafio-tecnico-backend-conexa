package com.felipe.integrationtests.model.dto.wrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe.integrationtests.model.dto.PatientDTO;

//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PatientEmbeddedDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("patient_dtolist")
	private List<PatientDTO> dtos;

	public PatientEmbeddedDTO() {
	}

	public List<PatientDTO> getDtos() {
		return dtos;
	}

	public void setDtos(List<PatientDTO> dtos) {
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
		PatientEmbeddedDTO other = (PatientEmbeddedDTO) obj;
		return Objects.equals(dtos, other.dtos);
	}

	@Override
	public String toString() {
		return "PatientEmbeddedDTO [dtos=" + dtos + "]";
	}

}
