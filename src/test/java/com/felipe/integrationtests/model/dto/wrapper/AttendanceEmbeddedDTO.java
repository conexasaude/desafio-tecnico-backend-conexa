package com.felipe.integrationtests.model.dto.wrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe.integrationtests.model.dto.AttendanceDTO;

//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AttendanceEmbeddedDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("attendanceDTOList")
	private List<AttendanceDTO> dtos;

	public AttendanceEmbeddedDTO() {
	}

	public List<AttendanceDTO> getDtos() {
		return dtos;
	}

	public void setDtos(List<AttendanceDTO> dtos) {
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
		AttendanceEmbeddedDTO other = (AttendanceEmbeddedDTO) obj;
		return Objects.equals(dtos, other.dtos);
	}

	@Override
	public String toString() {
		return "AttendanceEmbeddedDTO [dtos=" + dtos + "]";
	}

}
