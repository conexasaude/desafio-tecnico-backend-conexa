package br.com.conexa.vo;

import jakarta.persistence.Transient;

public class PacienteVO {

	
	@Transient
	private String nome;
	
	@Transient
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
	
	
	
	
}
