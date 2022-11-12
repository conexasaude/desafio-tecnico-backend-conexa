package com.juliagomes.desafiobackendconexa.model.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.modelmapper.ModelMapper;

import com.juliagomes.desafiobackendconexa.model.Agendamento;

public class AgendamentoDTO {

	@NotEmpty(message = "Preenchimento Obrigatório")
	@NotNull(message = "Preenchimento Obrigatório")
	@Pattern(regexp = "^\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d \\d\\d:\\d\\d:\\d\\d$", message = "É valido apenas o formato yyyy-mm-dd 00:00:00 ")
	private String dataHora;

	@NotNull(message = "Preenchimento Obrigatório")
	@Valid
	private PacienteDTO paciente;

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public PacienteDTO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteDTO paciente) {
		this.paciente = paciente;
	}

	public Agendamento toEntity() {

		ModelMapper mapper = new ModelMapper();

		return mapper.map(this, Agendamento.class);
	}
}
