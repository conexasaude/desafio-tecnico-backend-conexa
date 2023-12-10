package com.felipe.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance_tb")
public class Attendance implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
		
	@Column(name = "date_time")
	private LocalDateTime dateTime;
	
	@ManyToOne
    @JoinColumn(name = "patient_id")
	private Patient patient;
    
	public Attendance() {
	}

	public Attendance(LocalDateTime dateTime, Patient patient) {
		this.dateTime = dateTime;
		this.patient = patient;
	}

	public Attendance(UUID id, LocalDateTime dateTime, Patient patient) {
		this.id = id;
		this.dateTime = dateTime;
		this.patient = patient;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateTime, id, patient);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attendance other = (Attendance) obj;
		return Objects.equals(dateTime, other.dateTime) && Objects.equals(id, other.id)
				&& Objects.equals(patient, other.patient);
	}

	@Override
	public String toString() {
		return "Attendance [id=" + id + ", dateTime=" + dateTime + ", patient=" + patient + "]";
	}
}
