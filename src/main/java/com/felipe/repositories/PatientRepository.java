package com.felipe.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felipe.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

	Optional<Patient> findByEmail(String email);
	
	Optional<Patient> findByCpf(String cpf);
	
//	@Query("SELECT patient " +
//			"FROM Patient patient " + 
//			"WHERE patient.fullName =:fullName AND p.specialty =:specialty")
//	Patient findByFullNameAndSpecialty(
//			@Param("fullName") String fullName,
//			@Param("specialty") String specialty);

}
