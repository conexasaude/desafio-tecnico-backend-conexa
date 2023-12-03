package com.felipe.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.felipe.model.Patient;
import com.felipe.model.dto.v1.PatientDTO;


@Component
public class PatientMapper {
	
	public Patient toEntity(PatientDTO dto) {
		Patient entity = new Patient();
		entity.setId(dto.getKey());
		entity.setEmail(dto.getEmail());
		entity.setFullName(dto.getFullName());
		entity.setPassword(dto.getPassword());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setPhone(dto.getPhone());
		entity.setHealthInsurance(dto.getHealthInsurance());
		return entity;
	}
	
	public PatientDTO toDto(Patient entity) {
		PatientDTO dto = new PatientDTO();
		dto.setKey(entity.getId());
		dto.setEmail(entity.getEmail());
		dto.setFullName(entity.getFullName());
		dto.setPassword(entity.getPassword());
		dto.setCpf(entity.getCpf());
		dto.setBirthDate(entity.getBirthDate());
		dto.setPhone(entity.getPhone());
		dto.setHealthInsurance(entity.getHealthInsurance());
		return dto;
	}
	
	public List<PatientDTO> toDto(List<Patient> listEntity) {
		return listEntity.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
	
	public List<Patient> toEntity(List<PatientDTO> listDto) {
		return listDto.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}
}
