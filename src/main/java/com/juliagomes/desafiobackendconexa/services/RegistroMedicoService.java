package com.juliagomes.desafiobackendconexa.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.juliagomes.desafiobackendconexa.exception.ConexaDesafioAPIException;
import com.juliagomes.desafiobackendconexa.exception.DataIntegrityViolationException;
import com.juliagomes.desafiobackendconexa.model.RegistroMedico;
import com.juliagomes.desafiobackendconexa.model.Role;
import com.juliagomes.desafiobackendconexa.model.dto.RegistroMedicoDTO;
import com.juliagomes.desafiobackendconexa.repository.RegistroMedicoRepository;
import com.juliagomes.desafiobackendconexa.repository.RoleRepository;
import com.juliagomes.desafiobackendconexa.utils.ValidaCpf;

@Service
public class RegistroMedicoService {

	@Autowired
	private RegistroMedicoRepository medicoRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public RegistroMedicoDTO save(RegistroMedico medico) {

		if (medicoRepository.existsByCpf(medico.getCpf())) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");

		}

		if (medicoRepository.existsByEmail(medico.getEmail())) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema");
		}

		ValidaCpf validaCpf = new ValidaCpf();
		validaCpf.removeCaracteresEspeciais((medico.getCpf()));
		if (!validaCpf.isCPF(medico.getCpf())) {
			throw new ConexaDesafioAPIException(HttpStatus.BAD_REQUEST, "CPF inválido");
		}
		if (!medico.getSenha().contentEquals(medico.getConfirmacaoSenha())) {
			throw new ConexaDesafioAPIException(HttpStatus.BAD_REQUEST, "As senhas não coincidem");
		}

		medico.setSenha(passwordEncoder.encode(medico.getSenha()));
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		medico.setRoles(Collections.singleton(roles));
		System.out.println(medico.getRoles());
		return medicoRepository.save(medico).toDTO();
	}

}
