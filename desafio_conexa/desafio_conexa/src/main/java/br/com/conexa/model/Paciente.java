package br.com.conexa.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "paciente")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cdPaciente;
	
	@Column(name = "tx_cpf",  length = 14, unique = true)
	private String cpf;
	
	@Column(name = "tx_nome", length = 50)
	private String paciente;

	public Integer getCdPaciente() {
		return cdPaciente;
	}

	public void setCdPaciente(Integer cdPaciente) {
		this.cdPaciente = cdPaciente;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}
	
	
	

}
