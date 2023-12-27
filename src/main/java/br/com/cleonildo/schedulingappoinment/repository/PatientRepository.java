package br.com.cleonildo.schedulingappoinment.repository;

import br.com.cleonildo.schedulingappoinment.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}