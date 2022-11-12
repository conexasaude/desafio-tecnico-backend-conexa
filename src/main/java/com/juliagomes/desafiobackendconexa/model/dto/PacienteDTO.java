package com.juliagomes.desafiobackendconexa.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.modelmapper.ModelMapper;

import com.juliagomes.desafiobackendconexa.model.Paciente;

public class PacienteDTO {

	@NotEmpty(message = "Preenchimento Obrigatório")
	@NotNull(message = "Preenchimento Obrigatório")
	private String nome;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@NotNull(message = "Preenchimento Obrigatório")
	@Pattern(regexp = "^(([0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}))$", message = "CPF inválido ")
	private String cpf;


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

	public Paciente toEntity() {

		ModelMapper mapper = new ModelMapper();

		return mapper.map(this, Paciente.class);
	}
}
