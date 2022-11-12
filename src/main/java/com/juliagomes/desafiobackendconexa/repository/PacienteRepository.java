package com.juliagomes.desafiobackendconexa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juliagomes.desafiobackendconexa.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, String>{


	Paciente findPacienteByCpf(String cpf);


	
	
}
