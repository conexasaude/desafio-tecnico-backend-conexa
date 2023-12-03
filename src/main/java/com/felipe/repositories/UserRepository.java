package com.felipe.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felipe.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);
	
	@Query("SELECT user " +
			"FROM User user " + 
			"WHERE user.fullName =:fullName AND p.specialty =:specialty")
	User findByFullNameAndSpecialty(
			@Param("fullName") String fullName,
			@Param("specialty") String specialty);

}
