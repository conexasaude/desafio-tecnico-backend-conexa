package com.conexa.desafio.repository;

import com.conexa.desafio.models.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity, Integer> {

    Optional<PacienteEntity> findByNome(String nome);

    Boolean existsByNome(String nome);
}
