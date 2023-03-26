package com.conexa.saude.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conexa.saude.model.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByCpf(String cpf);

}
