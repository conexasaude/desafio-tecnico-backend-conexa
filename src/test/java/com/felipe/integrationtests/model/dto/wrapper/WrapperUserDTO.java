package com.felipe.integrationtests.model.dto.wrapper;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperUserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("_embedded")
	private UserEmbeddedDTO embeddedDTO;

	public WrapperUserDTO() {
	}

	public UserEmbeddedDTO getEmbeddedDTO() {
		return embeddedDTO;
	}

	public void setEmbeddedDTO(UserEmbeddedDTO embeddedDTO) {
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
		WrapperUserDTO other = (WrapperUserDTO) obj;
		return Objects.equals(embeddedDTO, other.embeddedDTO);
	}

	@Override
	public String toString() {
		return "WrapperUserDTO [embeddedDTO=" + embeddedDTO + "]";
	}
	
	
	
}
