package com.felipe.integrationtests.model.dto.wrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe.integrationtests.model.dto.UserDTO;

public class UserEmbeddedDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("user_dtolist")
	private List<UserDTO> dtos;

	public UserEmbeddedDTO() {
	}

	public List<UserDTO> getDtos() {
		return dtos;
	}

	public void setDtos(List<UserDTO> dtos) {
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
		UserEmbeddedDTO other = (UserEmbeddedDTO) obj;
		return Objects.equals(dtos, other.dtos);
	}

	@Override
	public String toString() {
		return "UserEmbeddedDTO [dtos=" + dtos + "]";
	}

}
