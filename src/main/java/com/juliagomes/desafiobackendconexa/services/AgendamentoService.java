package com.juliagomes.desafiobackendconexa.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.juliagomes.desafiobackendconexa.exception.ConexaDesafioAPIException;
import com.juliagomes.desafiobackendconexa.model.Agendamento;
import com.juliagomes.desafiobackendconexa.model.Paciente;
import com.juliagomes.desafiobackendconexa.model.RegistroMedico;
import com.juliagomes.desafiobackendconexa.model.dto.AgendamentoDTO;
import com.juliagomes.desafiobackendconexa.repository.AgendamentoRepository;
import com.juliagomes.desafiobackendconexa.repository.PacienteRepository;
import com.juliagomes.desafiobackendconexa.security.JwtTokenProvider;
import com.juliagomes.desafiobackendconexa.utils.ValidaCpf;

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public AgendamentoDTO save(Agendamento agendamento, String authorization) {
		
		String token = authorization.substring(7);
		String username = jwtTokenProvider.getUsernameFromJWT(token);
		
		String dia = agendamento.getDataHora().substring(0, 10);
		String hora = agendamento.getDataHora().substring(11);

		LocalDate diaAgendado = LocalDate.parse(dia);
		LocalTime horaAgendada = LocalTime.parse(hora);
		


		if (diaAgendado.isBefore(LocalDate.now())) {
			throw new ConexaDesafioAPIException(HttpStatus.BAD_REQUEST, "Data inválida. Informe uma data futura");
		} else if (diaAgendado.isEqual(LocalDate.now()) && horaAgendada.isBefore(LocalTime.now())) {
			throw new ConexaDesafioAPIException(HttpStatus.BAD_REQUEST, "Hora inválida. Informe uma hora futura");

		}
		
		ValidaCpf validaCpf = new ValidaCpf();
		validaCpf.removeCaracteresEspeciais(agendamento.getPaciente().getCpf());	
		if (!validaCpf.isCPF(agendamento.getPaciente().getCpf())) {
			throw new ConexaDesafioAPIException(HttpStatus.BAD_REQUEST, "CPF inválido");

		}
		


		Optional<Paciente> optional = pacienteRepository.findById(agendamento.getPaciente().getCpf());
		if (!optional.isPresent()) {
			pacienteRepository.save(agendamento.getPaciente());
		} else {
			Paciente paciente = pacienteRepository.findPacienteByCpf(agendamento.getPaciente().getCpf());
			if (paciente.getNome() != agendamento.getPaciente().getNome()) {
				agendamento.getPaciente().setNome(paciente.getNome());
			}
		}
		
		Optional<Agendamento> agendamentos = agendamentoRepository.findAgendamentoByPacienteHourAndDate(agendamento.getPaciente().getCpf(), agendamento.getDataHora());
		if(agendamentos.isPresent()) {
			throw new ConexaDesafioAPIException(HttpStatus.BAD_REQUEST, "Paciente possui um agendamento nesse mesmo horário e dia.");

		}

		
			RegistroMedico medico = new RegistroMedico();
			medico.setEmail(username);
		
		agendamento.setEmailMedico(username);
		return agendamentoRepository.save(agendamento).toDTO();
	}



}