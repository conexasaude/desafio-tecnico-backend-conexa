package com.felipe.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.felipe.controller.UserController;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.mapper.UserMapper;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.repositories.UserRepository;
import com.felipe.util.MessageUtils;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {
	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserMapper mapper;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username + "!");

		return repository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND + ": Email " + username + " not found!"));
	}

	public List<UserDTO> findAll() {
		logger.info("Finding All User");
		var users = repository.findAll();
		List<UserDTO> listPersisted = mapper.toDto(users);
		listPersisted.stream().forEach(doctor -> {
			try {
				addSelfRel(doctor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return listPersisted;
	}

	public UserDTO findById(String id) throws Exception {
		logger.info("Finding one User");
		
		UserDTO dto = repository.findById(UUID.fromString(id)).map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		return addSelfRel(dto);
	}
	
	@Transactional
	public UserDTO disableUser(String id) throws Exception {
		logger.info("Disabling one User");
		
		repository.disableUser(UUID.fromString(id));

		UserDTO dto = repository.findById(UUID.fromString(id)).map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		return addSelfRel(dto);
	}

	private UserDTO addSelfRel(UserDTO doctor) throws Exception {
	    return doctor.add(linkTo(methodOn(UserController.class).findById(doctor.getKey().toString())).withSelfRel());
	}


}
