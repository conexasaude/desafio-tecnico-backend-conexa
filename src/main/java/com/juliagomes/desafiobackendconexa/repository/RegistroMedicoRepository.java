package com.juliagomes.desafiobackendconexa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juliagomes.desafiobackendconexa.model.RegistroMedico;

public interface RegistroMedicoRepository extends JpaRepository<RegistroMedico, Integer>{

	Optional<RegistroMedico> findByEmail(String email);
	Boolean existsByEmail(String email);
	boolean existsByCpf(String cpf);
}
