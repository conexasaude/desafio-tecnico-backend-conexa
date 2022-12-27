package br.com.conexa.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.conexa.model.Medico;
import br.com.conexa.repositories.MedicoRepository;

@Service
public class UserServices implements UserDetailsService {

	private Logger logger = Logger.getLogger(UserServices.class.getName());

	@Autowired
	MedicoRepository medicoRepository;

	public UserServices(MedicoRepository medicoRepository) {
		this.medicoRepository = medicoRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("Finding one user by name " + username + "!");
		Medico medico = medicoRepository.findByEmail(username);

		if (medico != null) {
			return medico;
		} else {
			throw new UsernameNotFoundException("Email" + username + "not found!");
		}

	}

}
