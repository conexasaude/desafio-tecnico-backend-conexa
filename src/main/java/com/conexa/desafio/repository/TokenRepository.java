package com.conexa.desafio.repository;

import com.conexa.desafio.models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    @Override
    Optional<TokenEntity> findById(Integer integer);

    Boolean existsByToken(String token);

    void deleteByToken(String token);
}
