package com.felipe.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.repositories.UserRepository;
import com.felipe.util.MessageUtils;

@Service
public class UserService implements UserDetailsService {
	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	private UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username + "!");
		
		return repository.findByUserName(username)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND + ": Email " + username + " not found!"));
	}
	


}
