package br.com.conexa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.conexa.exceptions.DifferentPasswordsException;
import br.com.conexa.exceptions.RequiredObjectIsNullException;
import br.com.conexa.model.Medico;
import br.com.conexa.repositories.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	MedicoRepository repository;

	public Medico signUp(Medico medico) {

		if (medico == null) {
			throw new RequiredObjectIsNullException();
		}
		
		if(!medico.getSenha().matches(medico.getConfirmacaoSenha()))
		{
			throw new DifferentPasswordsException();
		}

		if (medico.getSenha() != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			medico.setSenha(encoder.encode(medico.getSenha()));
		}

		return repository.save(medico);
	}

}
