package com.juliagomes.desafiobackendconexa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.juliagomes.desafiobackendconexa.model.dto.PacienteDTO;

@Entity
@Table(name= "paciente")
public class Paciente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String cpf;
	private String nome;
	@OneToMany(mappedBy = "paciente")
	private List<Agendamento> agendamento = new ArrayList<>();

	public Paciente() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Agendamento> getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(List<Agendamento> agendamento) {
		this.agendamento = agendamento;
	}

	public PacienteDTO toDTO() {

		ModelMapper mapper = new ModelMapper();

		return mapper.map(this, PacienteDTO.class);
	}
}