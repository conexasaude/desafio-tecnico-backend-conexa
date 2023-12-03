package com.felipe.model.dto.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "email", "full_name", "specialty", "cpf", "birth_date", "phone" })
public class DoctorDTO extends UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String specialty;
	
	@JsonProperty("medical_license")
    private String medicalLicense;

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getMedicalLicense() {
		return medicalLicense;
	}

	public void setMedicalLicense(String medicalLicense) {
		this.medicalLicense = medicalLicense;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(medicalLicense, specialty);
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
		DoctorDTO other = (DoctorDTO) obj;
		return Objects.equals(medicalLicense, other.medicalLicense) && Objects.equals(specialty, other.specialty);
	}

	@Override
	public String toString() {
		String originalString = super.toString();
		String newProperties = "specialty=" + specialty + ", medicalLicense=" + medicalLicense;
		return  originalString.replace("UserDTO", "DoctorDTO") + ", " + newProperties + "]";
	}
	

	

}
