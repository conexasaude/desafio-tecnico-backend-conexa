package com.felipe.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.felipe.util.StringUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor_tb")
@EntityListeners(AuditingEntityListener.class)
public class Doctor implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "full_name", nullable = false, length = 160)
	private String fullName;
	
	@Column(name = "cpf", nullable = false, length = 14, unique = true)
	private String cpf;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Column(name = "phone", length = 45)
	private String phone;
	
	@Column(name = "specialty")
	private String specialty;

    @OneToOne
    private User user;
	
	@CreatedDate
	private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

	public Doctor() {
		super();
	}

	public Doctor(String email, String fullName, String cpf, LocalDate birthDate, String phone,
			String specialty) {
		this.email = email;
		this.fullName = fullName;
		this.cpf = StringUtil.removeNonNumeric(cpf);
		this.birthDate = birthDate;
		this.phone = phone;
		this.specialty = specialty;
	}

	public Doctor(UUID id, String email, String fullName, String cpf, LocalDate birthDate,
			String phone, String specialty) {
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.cpf = StringUtil.removeNonNumeric(cpf);
		this.birthDate = birthDate;
		this.phone = phone;
		this.specialty = specialty;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthDate, cpf, createdAt, email, fullName, id, phone, specialty, updatedAt,
				user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(email, other.email)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(id, other.id)
				&& Objects.equals(phone, other.phone)
				&& Objects.equals(specialty, other.specialty) && Objects.equals(updatedAt, other.updatedAt)
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", email=" + email + ", fullName=" + fullName + ", cpf="
				+ cpf + ", birthDate=" + birthDate + ", phone=" + phone + ", specialty=" + specialty + ", user=" + user
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}



}
