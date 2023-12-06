package com.felipe.mapper;

import org.springframework.stereotype.Component;


@Component
public class UserMapper {
	
//	public User toEntity(UserDTO dto) {
//		User entity = new User();
//		entity.setId(dto.getKey());
//		entity.setEmail(dto.getEmail());
//		entity.setFullName(dto.getFullName());
//		entity.setPassword(dto.getPassword());
//		entity.setSpecialty(dto.getSpecialty());
//		entity.setCpf(dto.getCpf());
//		entity.setBirthDate(DateUtil.convertStringToLocalDate(dto.getBirthDate()));
//		entity.setPhone(dto.getPhone());
////        entity.setCreatedAt(dto.getCreatedAt());
////        entity.setUpdatedAt(dto.getUpdatedAt());
//		return entity;
//	}
//	
//	public UserDTO toDto(User entity) {
//		UserDTO dto = new UserDTO();
//		dto.setKey(entity.getId());
//		dto.setEmail(entity.getEmail());
//		dto.setFullName(entity.getFullName());
//		dto.setPassword(entity.getPassword());
//		dto.setSpecialty(entity.getSpecialty());
//		dto.setCpf(entity.getCpf());
//		dto.setBirthDate(DateUtil.convertLocalDateToString(entity.getBirthDate()));
//		dto.setPhone(entity.getPhone());
////        dto.setCreatedAt(entity.getCreatedAt());
////        dto.setUpdatedAt(entity.getUpdatedAt());
//		return dto;
//	}
//	
//	public List<UserDTO> toDto(List<User> listEntity) {
//		return listEntity.stream()
//				.map(this::toDto)
//				.collect(Collectors.toList());
//	}
//	
//	public List<User> toEntity(List<UserDTO> listDto) {
//		return listDto.stream()
//				.map(this::toEntity)
//				.collect(Collectors.toList());
//	}
}
