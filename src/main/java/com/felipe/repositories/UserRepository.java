package com.felipe.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felipe.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);
	
	@Query("SELECT user " +
			"FROM User user " + 
			"WHERE user.fullName =?1 AND p.specialty =?2")
	User findByFullNameAndSpecialty(String fullName, String specialty);
}
