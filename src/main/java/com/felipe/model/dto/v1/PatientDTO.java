package com.felipe.model.dto.v1;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "email", "full_name", "specialty", "cpf", "birth_date", "phone", "health_insurance" })
public class PatientDTO extends UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@JsonProperty("health_insurance")
	private String healthInsurance;

    public PatientDTO() {
    	super();
    }
    
	public PatientDTO(UUID key, String email, String fullName, String password, String cpf,
			LocalDate birthDate, String phone, String healthInsurance) {
		super (key, email, fullName, password, cpf, birthDate, phone);
		this.healthInsurance = healthInsurance;
	}
	

	public String getHealthInsurance() {
		return healthInsurance;
	}

	public void setHealthInsurance(String healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(healthInsurance);
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
		return Objects.equals(healthInsurance, other.healthInsurance);
	}

	@Override
	public String toString() {
		String originalString = super.toString();
		String newProperties = "healthInsurance=" + healthInsurance;
		return  originalString.replace("UserDTO", "PatientDTO") + ", " + newProperties + "]";
	}
	

	

}
