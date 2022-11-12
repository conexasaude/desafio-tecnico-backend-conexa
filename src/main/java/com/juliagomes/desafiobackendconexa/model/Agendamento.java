package com.juliagomes.desafiobackendconexa.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.juliagomes.desafiobackendconexa.model.dto.AgendamentoDTO;

@Entity
@Table(name= "agendamento")
public class Agendamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String dataHora;
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "cpf", referencedColumnName = "cpf"),
			@JoinColumn(name = "nome", referencedColumnName = "nome") })
	private Paciente paciente;
	private String emailMedico;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {

		this.paciente = paciente;
	}

	
	
	public String getEmailMedico() {
		return emailMedico;
	}

	public void setEmailMedico(String emailMedico) {
		this.emailMedico = emailMedico;
	}

	public AgendamentoDTO toDTO() {

		ModelMapper mapper = new ModelMapper();

		return mapper.map(this, AgendamentoDTO.class);
	}

}
