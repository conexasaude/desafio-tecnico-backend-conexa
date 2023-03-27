package com.conexa.saude.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conexa.saude.model.entity.DoctorEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, String> {

    Optional<DoctorEntity> findByEmail(String email);

    Optional<DoctorEntity> findByCpf(String cpf);

}
