package com.felipe.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor_tb")
@PrimaryKeyJoinColumn(name = "doctor_id")
public class Doctor extends User {

	private static final long serialVersionUID = 1L;

	@Column(name = "specialty")
	private String specialty;

	@Column(name = "medical_license")
	private String medicalLicense;

	public Doctor() {
		super();
	}

	public Doctor(String email, String fullName, String password, String cpf, LocalDate birthDate, String phone,
			String specialty, String medicalLicense) {
		super(email, fullName, password, cpf, birthDate, phone);
		this.specialty = specialty;
		this.medicalLicense = medicalLicense;
	}

	public Doctor(UUID id, String email, String fullName, String password, String cpf, LocalDate birthDate,
			String phone, String specialty, String medicalLicense) {
		super(id, email, fullName, password, cpf, birthDate, phone);
		this.specialty = specialty;
		this.medicalLicense = medicalLicense;
	}

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
		Doctor other = (Doctor) obj;
		return Objects.equals(medicalLicense, other.medicalLicense) && Objects.equals(specialty, other.specialty);
	}

	@Override
	public String toString() {
		String originalString = super.toString();
		String newProperties = "specialty=" + specialty + ", medicalLicense=" + medicalLicense;
		return  originalString.replace("User", "Doctor") + ", " + newProperties + "]";
	}
}
