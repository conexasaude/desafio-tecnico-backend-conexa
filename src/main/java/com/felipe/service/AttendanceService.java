package com.felipe.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.controller.AttendanceController;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.mapper.AttendanceMapper;
import com.felipe.model.Attendance;
import com.felipe.model.Patient;
import com.felipe.model.dto.v1.AttendanceDTO;
import com.felipe.repositories.AttendanceRepository;
import com.felipe.repositories.PatientRepository;
import com.felipe.util.DateUtil;
import com.felipe.util.MessageUtils;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {
	private Logger logger = Logger.getLogger(DoctorService.class.getName());

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AttendanceRepository attendanceRepository;

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

			// Salve o novo paciente no banco de dados
			return patientRepository.save(newPatient);
		});

		// Agora que temos um paciente (existente ou recém-criado), crie o atendimento
		Attendance newAttendance = new Attendance();
		newAttendance.setDateTime(DateUtil.convertStringToLocalDateTime(attendanceDTO.getDateTime()));
		newAttendance.setPatient(existingPatient);

		return mapper.toDto(attendanceRepository.save(newAttendance));
	}

	public List<AttendanceDTO> findAll() {
		logger.info("Finding All Attendance");

		List<AttendanceDTO> listPersisted = mapper.toDto(attendanceRepository.findAll());
		listPersisted.stream().forEach(doctor -> {
			try {
				addSelfRel(doctor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return listPersisted;
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

		entity.setDateTime(Objects.requireNonNullElse(DateUtil.convertStringToLocalDateTime(dto.getDateTime()),
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
