package com.felipe.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient_tb")
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "full_name", nullable = false, length = 160)
	private String fullName;

	@Column(name = "cpf", nullable = false, length = 14, unique = true)
	private String cpf;

	@Column(name = "health_insurance", length = 25)
	private String healthInsurance;

    @OneToMany(mappedBy = "patient")
    private List<Attendance> attendances;
	
	public Patient() {
	}

	public Patient(String fullName, String cpf, String healthInsurance, List<Attendance> attendances) {
		this.fullName = fullName;
		this.cpf = cpf;
		this.healthInsurance = healthInsurance;
		this.attendances = attendances;
	}

	public Patient(UUID id, String fullName, String cpf, String healthInsurance, List<Attendance> attendances) {
		this.id = id;
		this.fullName = fullName;
		this.cpf = cpf;
		this.healthInsurance = healthInsurance;
		this.attendances = attendances;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getHealthInsurance() {
		return healthInsurance;
	}

	public void setHealthInsurance(String healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attendances, cpf, fullName, healthInsurance, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(attendances, other.attendances) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(healthInsurance, other.healthInsurance)
				&& Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", fullName=" + fullName + ", cpf=" + cpf + ", healthInsurance=" + healthInsurance
				+ ", attendances=" + attendances + "]";
	}

	

}
