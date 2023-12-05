package com.felipe.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient_tb")
@EntityListeners(AuditingEntityListener.class)
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

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	public Patient() {
	}

	public Patient(String fullName, String cpf, String healthInsurance) {
		this.fullName = fullName;
		this.cpf = cpf;
		this.healthInsurance = healthInsurance;
	}

	public Patient(UUID id, String fullName, String cpf, String healthInsurance) {
		this.id = id;
		this.fullName = fullName;
		this.cpf = cpf;
		this.healthInsurance = healthInsurance;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, createdAt, fullName, healthInsurance, id, updatedAt);
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
		return Objects.equals(cpf, other.cpf) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(healthInsurance, other.healthInsurance)
				&& Objects.equals(id, other.id) && Objects.equals(updatedAt, other.updatedAt);
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", fullName=" + fullName + ", cpf=" + cpf + ", healthInsurance=" + healthInsurance
				+ "]";
	}

}
