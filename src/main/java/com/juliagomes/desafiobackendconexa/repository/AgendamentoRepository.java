package com.juliagomes.desafiobackendconexa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.juliagomes.desafiobackendconexa.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {
	
	@Query(value = "SELECT * FROM agendamento  WHERE cpf = ?1 and dataHora = ?2",nativeQuery = true)
	Optional<Agendamento> findAgendamentoByPacienteHourAndDate(String cpf, String dataHora);

}
