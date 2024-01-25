package com.felipe.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.felipe.model.Doctor;
import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.util.CpfUtil;
import com.felipe.util.DateUtil;
import com.felipe.util.PhoneNumberUtil;



@Component
public class DoctorMapper {

	public Doctor toEntity(DoctorDTO dto) {
		Doctor entity = new Doctor();
		entity.setId(dto.getKey());
		entity.setEmail(dto.getEmail());
		entity.setFullName(dto.getFullName());
		entity.setSpecialty(dto.getSpecialty());
		entity.setCpf(CpfUtil.formatCPF(dto.getCpf()));
		entity.setBirthDate(DateUtil.convertStringToLocalDate(dto.getBirthDate()));
		entity.setPhone(PhoneNumberUtil.formatPhoneNumber(dto.getPhone()));
//        entity.setCreatedAt(dto.getCreatedAt());
//        entity.setUpdatedAt(dto.getUpdatedAt());
		return entity;
	}

	public DoctorDTO toDto(Doctor entity) {
		DoctorDTO dto = new DoctorDTO();
		dto.setKey(entity.getId());
		dto.setEmail(entity.getEmail());
		dto.setFullName(entity.getFullName());
		dto.setSpecialty(entity.getSpecialty());
		dto.setCpf(CpfUtil.formatCPF(entity.getCpf()));
		dto.setBirthDate(DateUtil.convertLocalDateToString(entity.getBirthDate()));
		dto.setPhone(entity.getPhone());
//        dto.setCreatedAt(entity.getCreatedAt());
//        dto.setUpdatedAt(entity.getUpdatedAt());
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
