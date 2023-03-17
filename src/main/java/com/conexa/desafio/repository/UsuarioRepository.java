package com.conexa.desafio.repository;

import com.conexa.desafio.models.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Integer> {
    UsuarioEntity findByEmail(String email);

    Boolean existsByEmail(String email);
}
