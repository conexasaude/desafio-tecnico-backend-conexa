package com.felipe.service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.model.User;
import com.felipe.repositories.UserRepository;
import com.felipe.util.MessageUtils;
import com.felipe.util.StringUtil;

@Service
public class UserService {
	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	UserRepository repository;

	public List<User> findAll() {
		logger.info("Finding All User");

		return repository.findAll();
	}

	public User findById(String id) {
		logger.info("Finding one user");

		return repository.findById(StringUtil.convertStringToUUID(id)).orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
	}

	public User create(User user) {
		logger.info("Create one user");

		return repository.save(user);
	}

	public User update(User user) {
		logger.info("Update one user");
		var entity = repository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		entity.setFullname(Objects.requireNonNullElse(user.getFullname(), entity.getFullname()));
		entity.setEmail(Objects.requireNonNullElse(user.getEmail(), entity.getEmail()));
		entity.setSpecialty(Objects.requireNonNullElse(user.getSpecialty(), entity.getSpecialty()));
		entity.setCpf(Objects.requireNonNullElse(user.getCpf(), entity.getCpf()));
		entity.setBirthDate(Objects.requireNonNullElse(user.getBirthDate(),entity.getBirthDate()));
		entity.setPhone(Objects.requireNonNullElse(user.getPhone(), entity.getPhone()));
		return repository.save(entity);
	}

	public void delete(String id) {
		logger.info("Deleting one user");
		var entity = repository.findById(StringUtil.convertStringToUUID(id)).orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		repository.delete(entity);
	}
}
