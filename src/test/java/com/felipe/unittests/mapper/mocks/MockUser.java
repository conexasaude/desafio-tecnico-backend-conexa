package com.felipe.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.felipe.model.User;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.utils.EmailGeneretor;
import com.github.javafaker.Faker;

public class MockUser {


	private Faker faker = new Faker();

	public User mockEntity() {
		return mockEntity(0);
	}

	public UserDTO mockDTO() {
		return mockDTO(0);
	}

	public List<User> mockEntityList() {
		List<User> listEntity = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			listEntity.add(mockEntity(i));
		}
		return listEntity;
	}

	public List<UserDTO> mockDTOList() {
		List<UserDTO> listDto = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			listDto.add(mockDTO(i));
		}
		return listDto;
	}

	public List<User> mockRandomEntityList(int quantity, boolean withId) {
		List<User> listEntity = new ArrayList<>();
		for (int i = 0; i < quantity; i++) {
			listEntity.add(mockRandomEntity(withId));
		}
		return listEntity;
	}

	public List<UserDTO> mockRandomDTOList(int quantity, boolean withId) {
		List<UserDTO> listDto = new ArrayList<>();
		for (int i = 0; i < quantity; i++) {
			listDto.add(mockRandomDTO(withId));
		}
		return listDto;
	}
	User entity = new User();
	
	public User mockEntity(Integer number) {
		User entity = new User();
		entity.setUsername("doctor.email" + number + "@gmail.com");
		entity.setAccountNonExpired(true);
		entity.setAccountNonLocked(true);
		entity.setCredentialsNonExpired(true);
		entity.setEnabled(true);
		entity.setConfirmedEmail(true);
		return entity;
	}

	public UserDTO mockDTO(Integer number) {
		UserDTO dto = new UserDTO();
		dto.setUsername("doctor.email" + number + "@gmail.com");
		dto.setAccountNonExpired(true);
		dto.setAccountNonLocked(true);
		dto.setCredentialsNonExpired(true);
		dto.setEnabled(true);
		dto.setConfirmedEmail(true);
		return dto;
	}

	public User mockRandomEntity(boolean withId) {
		UUID id = withId ? UUID.randomUUID() : null;
		String email = EmailGeneretor.generateEmail(faker.name().fullName());
		User entity = new User();
		entity.setId(id);
		entity.setUsername(email);
		entity.setPassword("default123");
		entity.setAccountNonExpired(true);
		entity.setAccountNonLocked(true);
		entity.setCredentialsNonExpired(true);
		entity.setEnabled(true);
		entity.setConfirmedEmail(true);
		return entity;
	}

	public UserDTO mockRandomDTO(boolean withId) {
		UUID id = withId ? UUID.randomUUID() : null;
		String email = EmailGeneretor.generateEmail(faker.name().fullName());
		UserDTO dto = new UserDTO();
		dto.setKey(id);
		dto.setUsername(email);
		entity.setPassword("default123");
		dto.setAccountNonExpired(true);
		dto.setAccountNonLocked(true);
		dto.setCredentialsNonExpired(true);
		dto.setEnabled(true);
		dto.setConfirmedEmail(true);
		return dto;
	}

}
