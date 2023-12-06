package com.felipe.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.felipe.repositories.UserRepository;

/**
 * Service class for managing doctor-related operations.
 */
@Service
public class UserService implements UserDetailsService {
	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	private UserRepository repository;

//	@Autowired
//	private UserMapper mapper;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		logger.info("Finding one user by name" + userName + "!");
		var user = repository.findByUserName(userName);
		if(user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username " + userName + " not found!");
		}
	}
	

}
