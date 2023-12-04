package com.felipe.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient_tb")
@PrimaryKeyJoinColumn(name = "patient_id")
public class Patient extends User {

	private static final long serialVersionUID = 1L;

	@Column(name = "health_insurance")
	private String healthInsurance;

	public Patient() {
		super();
	}

	public Patient(String email, String fullName, String password, String cpf, LocalDate birthDate, String phone,
			String healthInsurance) {
		super(email, fullName, password, cpf, birthDate, phone);
		this.healthInsurance = healthInsurance;
	}

	public Patient(UUID id, String email, String fullName, String password, String cpf, LocalDate birthDate,
			String phone, String healthInsurance) {
		super(id, email, fullName, password, cpf, birthDate, phone);
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
		Patient other = (Patient) obj;
		return Objects.equals(healthInsurance, other.healthInsurance);
	}

	@Override
	public String toString() {
		String originalString = super.toString();
		String newProperties = "healthInsurance=" + healthInsurance;
		return originalString.replace("User", "Patient") + ", " + newProperties + "]";
	}

}
