package com.felipe.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felipe.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

	Optional<Patient> findByFullName(String fullName);

	Optional<Patient> findByCpf(String cpf);
	
    @Query("SELECT p FROM Patient p WHERE LOWER(p.fullName) LIKE LOWER(CONCAT('%', :partialName, '%'))")
    Page<Patient> findByPartialName(@Param("partialName") String partialName, Pageable pageable);
}
