package com.juliagomes.desafiobackendconexa.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.juliagomes.desafiobackendconexa.model.RegistroMedico;

public class RegistroMedicoDTO {

	@NotEmpty(message = "Preenchimento Obrigatório")
	@NotNull(message = "Preenchimento Obrigatório")
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email inválido ")
	private String email;

	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[!#@$%&])(?=.*[0-9])(?=.*[a-z]).{6,15}$", message = "Senha inválida. Deve conter: 6 a 15 caracteres. Letras maiúsculas e minúsculas e no mínimo um caractere especial.")
	@NotEmpty(message = "Preenchimento Obrigatório")
	@NotNull(message = "Preenchimento Obrigatório")
	private String senha;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@NotNull(message = "Preenchimento Obrigatório")
	private String confirmacaoSenha;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@NotNull(message = "Preenchimento Obrigatório")
	@Size(max = 50, message = "Acima do limite de caracteres definido.")
	private String especialidade;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@NotNull(message = "Preenchimento Obrigatório")
	@Pattern(regexp = "^(([0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}))$", message = "CPF inválido. Formato correto xxx.xxx.xxx-xx")
	private String cpf;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@NotNull(message = "Preenchimento Obrigatório")
	@Pattern(regexp = "^\\d\\d/\\d\\d/\\d\\d\\d\\d$", message = "Data inválida. Formato correto xx/xx/xxxx")
	private String dataNascimento;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@NotNull(message = "Preenchimento Obrigatório")
	@Pattern(regexp = "^\\(\\d\\d\\) \\d\\d\\d\\d\\-\\d\\d\\d\\d$", message = "Telefone inválido. Formato ideal (xx) xxxx-xxxx")
	private String telefone;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public RegistroMedico toEntity() {

		ModelMapper mapper = new ModelMapper();

		return mapper.map(this, RegistroMedico.class);
	}

}