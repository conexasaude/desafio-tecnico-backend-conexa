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
		entity.setId(dto.getKey());
		entity.setUserName(dto.getUserName());
		entity.setAccountNonExpired(dto.getAccountNonExpired());
		entity.setAccountNonLocked(dto.getAccountNonLocked());
		entity.setCredentialsNonExpired(dto.getCredentialsNonExpired());
		entity.setEnabled(dto.getEnabled());
		entity.setConfirmedEmail(dto.getConfirmedEmail());
		return entity;
	}
	
	public UserDTO toDto(User entity) {
		UserDTO dto = new UserDTO();
		dto.setKey(entity.getId());
		dto.setUserName(entity.getUserName());
		dto.setAccountNonExpired(entity.getAccountNonExpired());
		dto.setAccountNonLocked(entity.getAccountNonLocked());
		dto.setCredentialsNonExpired(entity.getCredentialsNonExpired());
		dto.setEnabled(entity.getEnabled());
		dto.setConfirmedEmail(entity.getConfirmedEmail());
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
