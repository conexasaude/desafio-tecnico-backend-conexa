package com.conexa.saude.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.conexa.saude.model.entity.InvalidTokenEntity;

import jakarta.transaction.Transactional;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidTokenEntity, String> {

	@Transactional
	@Modifying
	@Query("DELETE FROM InvalidTokenEntity WHERE expirationDate < CURDATE()")
	void deleteOldTokens();

	Optional<InvalidTokenEntity> findByToken(String token);

}
