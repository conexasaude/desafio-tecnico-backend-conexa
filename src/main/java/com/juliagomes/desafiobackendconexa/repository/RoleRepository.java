package com.juliagomes.desafiobackendconexa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juliagomes.desafiobackendconexa.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Optional<Role> findByName(String name);

}
