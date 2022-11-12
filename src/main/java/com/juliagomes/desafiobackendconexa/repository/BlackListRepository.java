package com.juliagomes.desafiobackendconexa.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.juliagomes.desafiobackendconexa.model.BlackListToken;

public interface BlackListRepository extends JpaRepository<BlackListToken, String> {

	@Query(value = "SELECT * FROM blacklisttoken  WHERE username = ?1 and token = ?2",nativeQuery = true)
	Optional<BlackListToken> findByEmailAndToken(String username, String token);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM blacklisttoken WHERE date < CURDATE();",nativeQuery = true)
	void deleteExpiredTokens();

}
