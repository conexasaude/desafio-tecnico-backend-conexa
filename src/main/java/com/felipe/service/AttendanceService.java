package com.felipe.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.felipe.controller.AttendanceController;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.mapper.AttendanceMapper;
import com.felipe.model.Attendance;
import com.felipe.model.Patient;
import com.felipe.model.dto.v1.AttendanceDTO;
import com.felipe.repositories.AttendanceRepository;
import com.felipe.repositories.PatientRepository;
import com.felipe.util.MessageUtils;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {
	private Logger logger = Logger.getLogger(AttendanceService.class.getName());

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private PagedResourcesAssembler<AttendanceDTO> assembler;

	@Autowired
	private AttendanceMapper mapper;

	@Transactional
	public AttendanceDTO createAttendanceWithPatient(AttendanceDTO attendanceDTO) {
		// Verifique se o paciente já existe no banco de dados com base no CPF
		Optional<Patient> optionalExistingPatient = patientRepository.findByCpf(attendanceDTO.getPatient().getCpf());

		Patient existingPatient = optionalExistingPatient.orElseGet(() -> {
			// Se o paciente não existir, crie um novo paciente
			Patient newPatient = new Patient();
			newPatient.setFullName(attendanceDTO.getPatient().getFullName());
			newPatient.setCpf(attendanceDTO.getPatient().getCpf());
			newPatient.setHealthInsurance(attendanceDTO.getPatient().getHealthInsurance());

			// Salve o novo paciente no banco de dados
			return patientRepository.save(newPatient);
		});

		// Agora que temos um paciente (existente ou recém-criado), crie o atendimento
		Attendance newAttendance = new Attendance();
		newAttendance.setDateTime((attendanceDTO.getDateTime()));
		newAttendance.setPatient(existingPatient);

		return mapper.toDto(attendanceRepository.save(newAttendance));
	}

	public PagedModel<EntityModel<AttendanceDTO>> findAll(Pageable pageable) throws Exception {
		logger.info("Finding All Doctor");

		Page<Attendance> entityPage = attendanceRepository.findAll(pageable);
		Page<AttendanceDTO> dtoPage = entityPage.map(entity -> mapper.toDto(entity));
		dtoPage .map(dto -> {
			try {
				return addSelfRel(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return dto;
		});
		Link link = linkTo(
			methodOn(AttendanceController.class)
			.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
				.withSelfRel();
		return assembler.toModel(dtoPage, link);
	}
	
	public PagedModel<EntityModel<AttendanceDTO>> findByDateTimeBetween(LocalDateTime initialDate, LocalDateTime endDate, Pageable pageable) throws Exception {
		logger.info("Finding All Doctor ByName");

		Page<Attendance> entityPage = attendanceRepository.findByDateTimeBetween(initialDate, endDate, pageable);
		Page<AttendanceDTO> dtoPage = entityPage.map(entity -> mapper.toDto(entity));
		dtoPage.map(dto -> {
			try {
				return addSelfRel(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return dto;
		});
		Link link = linkTo(
			methodOn(AttendanceController.class)
			.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
				.withSelfRel();
		return assembler.toModel(dtoPage, link);
	}

	public AttendanceDTO findById(String id) throws Exception {
		logger.info("Finding one Attendance");

		AttendanceDTO dto = attendanceRepository.findById(UUID.fromString(id)).map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		return addSelfRel(dto);
	}

	public AttendanceDTO update(AttendanceDTO dto) throws Exception {
		logger.info("Update one Attendance");

		Attendance entity = attendanceRepository.findById(dto.getKey())
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));

		entity.setDateTime(Objects.requireNonNullElse((dto.getDateTime()),
				entity.getDateTime()));

		AttendanceDTO dtoUpdated = mapper.toDto(attendanceRepository.save(entity));
		return addSelfRel(dtoUpdated);
	}

	public void delete(String id) {
		logger.info("Deleting one Attendance");
		Attendance entity = attendanceRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		attendanceRepository.delete(entity);
	}

	private AttendanceDTO addSelfRel(AttendanceDTO dto) throws Exception {
		return dto
				.add(linkTo(methodOn(AttendanceController.class).findById(dto.getKey().toString())).withSelfRel());
	}
}
