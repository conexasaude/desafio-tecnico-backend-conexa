package br.com.cleonildo.schedulingappoinment.repository;

import br.com.cleonildo.schedulingappoinment.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<UserDetails> findByEmail(String email);
}