package com.conexa.saude.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conexa.saude.model.entity.Paciente;

@Repository
public interface PatientRepository extends JpaRepository<Paciente, String> {

    Optional<Paciente> findByCpf(String cpf);

}
