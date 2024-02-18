package com.felipe.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.felipe.model.Attendance;
import com.felipe.model.dto.v1.AttendanceDTO;

@Component
public class AttendanceMapper {

	@Autowired
	PatientMapper patientMapper;

	public Attendance toEntity(AttendanceDTO dto) {
		Attendance entity = new Attendance();
		entity.setId(dto.getKey());
		entity.setDateTime((dto.getDateTime()));
		entity.setPatient(patientMapper.toEntity(dto.getPatient()));
		return entity;
	}

	public AttendanceDTO toDto(Attendance entity) {
		AttendanceDTO dto = new AttendanceDTO();
		dto.setKey(entity.getId());
		dto.setDateTime((entity.getDateTime()));
		dto.setPatient(patientMapper.toDto(entity.getPatient()));
		return dto;
	}

	public List<AttendanceDTO> toDto(List<Attendance> listEntity) {
		return listEntity.stream().map(this::toDto).collect(Collectors.toList());
	}

	public List<Attendance> toEntity(List<AttendanceDTO> listDto) {
		return listDto.stream().map(this::toEntity).collect(Collectors.toList());
	}
}
