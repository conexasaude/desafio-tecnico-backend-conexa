package com.felipe.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.controller.UserController;
import com.felipe.exceptions.BadRequestException;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.mapper.UserMapper;
import com.felipe.model.User;
import com.felipe.model.dto.v1.PasswordUpdateDTO;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.repositories.UserRepository;
import com.felipe.util.MessageUtils;

/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {
	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserMapper mapper;

    /**
     * Retrieves a list of all users.
     *
     * @return A list of UserDTO objects.
     */
	public List<UserDTO> findAll() {
		logger.info("Finding All User");

		List<UserDTO> listUsers = mapper.toDto(repository.findAll());
		listUsers.stream().forEach(user -> {
			try {
				addUserSelfRel(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return listUsers;
	}

    /**
     * Retrieves a user by their ID.
     *
     * @param id: The ID of the user.
     * @return The UserDTO object representing the user.
     * @throws ResourceNotFoundException: If no user is found with the given ID.
     */
	public UserDTO findById(String id) throws Exception {
		logger.info("Finding one user");

		UserDTO user = repository.findById(UUID.fromString(id)).map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		return addUserSelfRel(user);

	}

    /**
     * Creates a new user.
     *
     * @param dto: The UserDTO object containing user information.
     * @return The created UserDTO object.
     * @throws BadRequestException: If the email provided already exists.
     */
	public UserDTO create(UserDTO dto) throws Exception {
		logger.info("Create one user");

		repository.findByEmail(dto.getEmail()).ifPresent(existingUser -> {
			throw new BadRequestException("Email " + MessageUtils.RECORDS_ALREADY_EXIST + ": " + dto.getEmail());
		});

		User entity = mapper.toEntity(dto);
		UserDTO user = mapper.toDto(repository.save(entity));

		return addUserSelfRel(user);

	}
    /**
     * Updates an existing user.
     *
     * @param dto: The UserDTO object containing updated user information.
     * @return The updated UserDTO object.
     * @throws ResourceNotFoundException: If no user is found with the given ID.
     */
	public UserDTO update(UserDTO dto) throws Exception {
		logger.info("Update one user");

		User entity = repository.findById(dto.getKey())
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		entity.setFullName(Objects.requireNonNullElse(dto.getFullName(), entity.getFullName()));
		entity.setEmail(Objects.requireNonNullElse(dto.getEmail(), entity.getEmail()));
		entity.setSpecialty(Objects.requireNonNullElse(dto.getSpecialty(), entity.getSpecialty()));
		entity.setCpf(Objects.requireNonNullElse(dto.getCpf(), entity.getCpf()));
		entity.setBirthDate(Objects.requireNonNullElse(dto.getBirthDate(), entity.getBirthDate()));
		entity.setPhone(Objects.requireNonNullElse(dto.getPhone(), entity.getPhone()));

		UserDTO user = mapper.toDto(repository.save(entity));
		return addUserSelfRel(user);

	}

    /**
     * Changes the password for a user.
     *
     * @param id: The ID of the user.
     * @param passwordUpdateDTO: The DTO containing old and new password information.
     * @throws BadRequestException: If old password is incorrect or new password doesn't match the confirmation.
     * @throws ResourceNotFoundException: If no user is found with the given ID.
     */
	public void changePassword(String id, PasswordUpdateDTO passwordUpdateDTO) {
		logger.info("Changing password");

		User entity = repository.findById(UUID.fromString(id))
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		if (passwordUpdateDTO.getOldPassword().equals(entity.getPassword())) {
			if (passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmNewPassword())) {
				entity.setPassword(passwordUpdateDTO.getNewPassword());
				repository.save(entity);
			} else {
				throw new BadRequestException("New password and confirm new password is not matches");
			}
		} else {
			throw new BadRequestException("Old password is incorret");
		}
	}
	
    /**
     * Deletes a user by their ID.
     *
     * @param id: The ID of the user to be deleted.
     * @throws ResourceNotFoundException: If no user is found with the given ID.
     */
	public void delete(String id) {
		logger.info("Deleting one user");
		User entity = repository.findById(UUID.fromString(id))
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		repository.delete(entity);
	}
	
    /**
     *     		
     * @param user: The User object containing user information
     * @return The UserDTO with self-rel link 
     * @throws Exception
     */
	private UserDTO addUserSelfRel(UserDTO user) throws Exception {
	    return user.add(linkTo(methodOn(UserController.class).findById(user.getKey().toString())).withSelfRel());
	}
}
