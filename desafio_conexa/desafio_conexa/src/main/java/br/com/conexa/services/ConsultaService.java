package br.com.conexa.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conexa.exceptions.InvalidDateException;
import br.com.conexa.exceptions.RequiredObjectIsNullException;
import br.com.conexa.model.Consulta;
import br.com.conexa.model.Paciente;
import br.com.conexa.repositories.ConsultaRepository;
import br.com.conexa.repositories.PacienteRepository;

@Service
public class ConsultaService {

	@Autowired
	ConsultaRepository repository;
	
	@Autowired
	PacienteRepository pacienteRepository;

	

	public Consulta bookAttendance(Consulta consulta) {
		if (consulta == null || consulta.getPacienteVO() == null) {
			throw new RequiredObjectIsNullException();
		}
		
		
		if(consulta.getDataHora().before(new Date()))
		{
			throw new InvalidDateException();
		}
		
		
		if(consulta.getPacienteVO().getCpf().isEmpty())
		{
			
			throw new RequiredObjectIsNullException();
			
			
		}
			
			Paciente paciente = pacienteRepository.findByCpf(consulta.getPacienteVO().getCpf());
			
			if(paciente == null)
			{
				paciente = new Paciente();
				paciente.setCpf(consulta.getPacienteVO().getCpf());
				paciente.setPaciente(consulta.getPacienteVO().getNome());
				paciente = pacienteRepository.save(paciente);
			}
		
			
			consulta.setCdPaciente(paciente.getCdPaciente());
			
			return repository.save(consulta);
			
		
	}

}
