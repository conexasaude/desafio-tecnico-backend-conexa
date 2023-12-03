package com.felipe.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.felipe.model.Doctor;
import com.felipe.model.dto.v1.DoctorDTO;


@Component
public class DoctorMapper {
	
	public Doctor toEntity(DoctorDTO dto) {
		Doctor entity = new Doctor();
		entity.setId(dto.getKey());
		entity.setEmail(dto.getEmail());
		entity.setFullName(dto.getFullName());
		entity.setPassword(dto.getPassword());
		entity.setSpecialty(dto.getSpecialty());
		entity.setMedicalLicense(dto.getMedicalLicense());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setPhone(dto.getPhone());
		return entity;
	}
	
	public DoctorDTO toDto(Doctor entity) {
		DoctorDTO dto = new DoctorDTO();
		dto.setKey(entity.getId());
		dto.setEmail(entity.getEmail());
		dto.setFullName(entity.getFullName());
		dto.setPassword(entity.getPassword());
		dto.setSpecialty(entity.getSpecialty());
		dto.setMedicalLicense(entity.getMedicalLicense());
		dto.setCpf(entity.getCpf());
		dto.setBirthDate(entity.getBirthDate());
		dto.setPhone(entity.getPhone());
		return dto;
	}
	
	public List<DoctorDTO> toDto(List<Doctor> listEntity) {
		return listEntity.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
	
	public List<Doctor> toEntity(List<DoctorDTO> listDto) {
		return listDto.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}
}
