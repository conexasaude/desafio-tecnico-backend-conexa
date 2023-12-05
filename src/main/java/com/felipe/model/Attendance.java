package com.felipe.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Serializable {

	
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
	
	@Column(name = "cpf", nullable = false, length = 14, unique = true)
	private String cpf;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Column(name = "phone", length = 45)
	private String phone;
	
	public User() {
		super();
	}

	public User(String email, String fullName, String password, String cpf, LocalDate birthDate, String phone) {
		super();
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.phone = phone;
	}

	public User(UUID id, String email, String fullName, String password, String cpf, LocalDate birthDate,
			String phone) {
		super();
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.password = password;
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
		return Objects.hash(birthDate, cpf, email, fullName, id, password, phone);
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
				&& Objects.equals(phone, other.phone);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", fullName=" + fullName + ", password=" + password + ", cpf="
				+ cpf + ", birthDate=" + birthDate + ", phone=" + phone + "]";
	}

}
