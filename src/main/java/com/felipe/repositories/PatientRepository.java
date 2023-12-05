package com.felipe.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felipe.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

	Optional<Patient> findByFullName(String fullName);
	
	Optional<Patient> findByCpf(String cpf);
}
