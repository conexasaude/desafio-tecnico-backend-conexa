package com.felipe.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipe.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
