package com.felipe.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felipe.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

	Optional<Doctor> findByEmail(String email);

	Optional<Doctor> findByCpf(String cpf);

	@Query("SELECT doctor " +
			"FROM Doctor doctor " +
			"WHERE doctor.fullName =:fullName AND p.specialty =:specialty")
	Doctor findByFullNameAndSpecialty(
			@Param("fullName") String fullName,
			@Param("specialty") String specialty);

}
