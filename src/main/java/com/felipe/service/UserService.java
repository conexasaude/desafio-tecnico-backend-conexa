package com.felipe.service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.exceptions.BadRequestException;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.mapper.UserMapper;
import com.felipe.model.User;
import com.felipe.model.dto.v1.PasswordUpdateDTO;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.repositories.UserRepository;
import com.felipe.util.MessageUtils;
import com.felipe.util.StringUtil;

@Service
public class UserService {
	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	UserRepository repository;

	@Autowired
	UserMapper mapper;

	public List<UserDTO> findAll() {
		logger.info("Finding All User");

		return mapper.toDto(repository.findAll());
	}

	public UserDTO findById(String id) {
		logger.info("Finding one user");

		return repository.findById(StringUtil.convertStringToUUID(id)).map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
	}

	public UserDTO create(UserDTO dto) {
		logger.info("Create one user");

		User entity = mapper.toEntity(dto);

		return mapper.toDto(repository.save(entity));
	}

	public UserDTO update(UserDTO dto) {
		logger.info("Update one user");

		User entity = repository.findById(dto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		entity.setFullname(Objects.requireNonNullElse(dto.getFullname(), entity.getFullname()));
		entity.setEmail(Objects.requireNonNullElse(dto.getEmail(), entity.getEmail()));
		entity.setSpecialty(Objects.requireNonNullElse(dto.getSpecialty(), entity.getSpecialty()));
		entity.setCpf(Objects.requireNonNullElse(dto.getCpf(), entity.getCpf()));
		entity.setBirthDate(Objects.requireNonNullElse(dto.getBirthDate(), entity.getBirthDate()));
		entity.setPhone(Objects.requireNonNullElse(dto.getPhone(), entity.getPhone()));

		return mapper.toDto(repository.save(entity));
	}

	public void changePassword(String id, PasswordUpdateDTO passwordUpdateDTO) {
		User entity = repository.findById(StringUtil.convertStringToUUID(id))
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

	public void delete(String id) {
		logger.info("Deleting one user");
		User entity = repository.findById(StringUtil.convertStringToUUID(id))
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		repository.delete(entity);
	}
}
