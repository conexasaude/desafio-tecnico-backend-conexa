package com.juliagomes.desafiobackendconexa.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.juliagomes.desafiobackendconexa.model.RegistroMedico;
import com.juliagomes.desafiobackendconexa.model.Role;
import com.juliagomes.desafiobackendconexa.repository.RegistroMedicoRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private RegistroMedicoRepository medicoRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		RegistroMedico medico = medicoRepository.findByEmail(email).

				orElseThrow(() -> new UsernameNotFoundException("User not found with username or email" + email));

		return new org.springframework.security.core.userdetails.User(medico.getEmail(), medico.getSenha(),
				mapRolesToAuthorities(medico.getRoles()));

	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
