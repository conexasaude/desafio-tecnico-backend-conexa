package com.conexa.desafio.repository;

import com.conexa.desafio.models.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Integer> {


}
