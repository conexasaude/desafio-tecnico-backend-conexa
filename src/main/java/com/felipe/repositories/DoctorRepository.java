package com.felipe.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felipe.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

	Optional<Doctor> findByEmail(String email);

	Optional<Doctor> findByCpf(String cpf);

	@Query("SELECT doctor " + "FROM Doctor doctor "
			+ "WHERE doctor.fullName =:fullName AND doctor.specialty =:specialty")
	Doctor findByFullNameAndSpecialty(@Param("fullName") String fullName, @Param("specialty") String specialty);

    @Query("SELECT d FROM Doctor d WHERE LOWER(d.fullName) LIKE LOWER(CONCAT('%', :partialName, '%'))")
    Page<Doctor> findByPartialName(@Param("partialName") String partialName, Pageable pageable);


}
