package com.felipe.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.felipe.controller.UserController;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.mapper.UserMapper;
import com.felipe.model.User;
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

	@Autowired
	private PagedResourcesAssembler<UserDTO> assembler;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username + "!");

		return repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(
				MessageUtils.NO_RECORDS_FOUND + ": Email " + username + " not found!"));
	}

	public UserDTO findByEmail(String username) throws Exception {

		logger.info("Finding one user by name " + username + "!");

		UserDTO dto = repository.findByUsername(username).map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));

		return addSelfRel(dto);
	}

	public PagedModel<EntityModel<UserDTO>> findAll(Pageable pageable) throws Exception {

		logger.info("Finding All User");

		Page<User> entityPage = repository.findAll(pageable);
		Page<UserDTO> dtoPage = entityPage.map(d -> mapper.toDto(d));
		dtoPage.map(user -> {
			try {
				return addSelfRel(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return user;
		});
		Link link = linkTo(
				methodOn(UserController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
				.withSelfRel();
		return assembler.toModel(dtoPage, link);
	}

	public UserDTO findById(String id) throws Exception {
		logger.info("Finding one User");

		UserDTO dto = repository.findById(UUID.fromString(id)).map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		return addSelfRel(dto);
	}

	@Transactional
	public String disableUser(String id) throws Exception {
		logger.info("Disabling one User");

		repository.disableUser(UUID.fromString(id));

		return "User has been disabled";
	}

	@Transactional
	public String confirmEmail(String id) throws Exception {
		logger.info("Confirm email of User");

		repository.confirmEmail(UUID.fromString(id));

		return "The user had their email confirmed";
	}

	private UserDTO addSelfRel(UserDTO dto) throws Exception {
		return dto.add(linkTo(methodOn(UserController.class).findById(dto.getKey().toString())).withSelfRel());
	}

}
