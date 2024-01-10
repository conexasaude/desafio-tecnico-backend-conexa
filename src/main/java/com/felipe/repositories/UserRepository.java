package com.felipe.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felipe.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByUsername(@Param("username") String username);
	
	@Modifying
	@Query("UPDATE User u SET u.enabled = false WHERE u.id =:id")
	void disableUser(@Param("id") UUID id);
	
	
	@Modifying
	@Query("UPDATE User u SET u.confirmedEmail = true WHERE u.id =:id")
	void confirmEmail(@Param("id") UUID id);
}
