package com.felipe.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.felipe.model.Patient;
import com.felipe.model.dto.v1.PatientDTO;
import com.felipe.util.CpfUtil;


@Component
public class PatientMapper {
	
	public Patient toEntity(PatientDTO dto) {
		Patient entity = new Patient();
		entity.setId(dto.getKey());
		entity.setFullName(dto.getFullName());
		entity.setCpf(CpfUtil.formatCPF(dto.getCpf()));
		entity.setHealthInsurance(dto.getHealthInsurance());
//        entity.setCreatedAt(dto.getCreatedAt());
//        entity.setUpdatedAt(dto.getUpdatedAt());
		return entity;
	}
	
	public PatientDTO toDto(Patient entity) {
		PatientDTO dto = new PatientDTO();
		dto.setKey(entity.getId());
		dto.setFullName(entity.getFullName());
		dto.setCpf(entity.getCpf());
		dto.setHealthInsurance(entity.getHealthInsurance());
//        dto.setCreatedAt(entity.getCreatedAt());
//        dto.setUpdatedAt(entity.getUpdatedAt());
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
