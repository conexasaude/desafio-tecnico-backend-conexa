package com.felipe.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.felipe.model.User;
import com.felipe.model.dto.v1.UserDTO;


@Component
public class UserMapper {
	
	public User toEntity(UserDTO dto) {
		User entity = new User();
		entity.setId(dto.getId());
		entity.setEmail(dto.getEmail());
		entity.setFullname(dto.getFullname());
		entity.setPassword(dto.getPassword());
		entity.setSpecialty(dto.getSpecialty());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setPhone(dto.getPhone());
		return entity;
	}
	
	public UserDTO toDto(User entity) {
		UserDTO dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setEmail(entity.getEmail());
		dto.setFullname(entity.getFullname());
		dto.setPassword(entity.getPassword());
		dto.setSpecialty(entity.getSpecialty());
		dto.setCpf(entity.getCpf());
		dto.setBirthDate(entity.getBirthDate());
		dto.setPhone(entity.getPhone());
		return dto;
	}
	
	public List<UserDTO> toDto(List<User> listEntity) {
		return listEntity.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
	
	public List<User> toEntity(List<UserDTO> listDto) {
		return listDto.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}
}
