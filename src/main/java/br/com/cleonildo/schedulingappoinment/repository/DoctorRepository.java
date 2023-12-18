package br.com.cleonildo.schedulingappoinment.repository;

import br.com.cleonildo.schedulingappoinment.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}