package com.felipe.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_tb")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "full_name", nullable = false, length = 160)
	private String fullName;

	@Column(name = "password")
	private String password;
	
	@Column(name = "specialty")
	private String specialty;
	
	@Column(name = "cpf", nullable = false, length = 14, unique = true)
	private String cpf;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Column(name = "phone", length = 20)
	private String phone;
	
    public User() {
    }
    
	public User(UUID id, String email, String fullName, String password, String specialty, String cpf,
			LocalDate birthDate, String phone) {
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.specialty = specialty;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.phone = phone;
	}
	
	public User(String email, String fullName, String password, String specialty, String cpf,
			LocalDate birthDate, String phone) {
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.specialty = specialty;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.phone = phone;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
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

	@Override
	public int hashCode() {
		return Objects.hash(birthDate, cpf, email, fullName, id, password, phone, specialty);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(email, other.email) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(phone, other.phone) && Objects.equals(specialty, other.specialty);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", fullName=" + fullName + ", password=" + password
				+ ", specialty=" + specialty + ", cpf=" + cpf + ", birthDate=" + birthDate + ", phone=" + phone + "]";
	}
	
	

}
