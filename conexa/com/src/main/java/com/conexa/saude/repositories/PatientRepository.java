package com.conexa.saude.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conexa.saude.model.entity.PacienteEntity;

@Repository
public interface PatientRepository extends JpaRepository<PacienteEntity, String> {

    Optional<PacienteEntity> findByCpf(String cpf);

}
