package com.felipe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.mapper.AttendanceMapper;
import com.felipe.model.Attendance;
import com.felipe.model.Patient;
import com.felipe.model.dto.v1.AttendanceDTO;
import com.felipe.repositories.AttendanceRepository;
import com.felipe.repositories.PatientRepository;
import com.felipe.util.DateUtil;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private AttendanceMapper attendanceMapper;

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

		return attendanceMapper.toDto(attendanceRepository.save(newAttendance));
	}

	/**
	 * 
	 * @param user: The Patient object containing user information
	 * @return The PatientDTO with self-rel link
	 * @throws Exception
	 */
//    private AttendanceDTO addPatientSelfRel(PatientDTO user) throws Exception {
//    	return (PatientDTO) user.add(linkTo(methodOn(PatientController.class).findById(user.getKey().toString())).withSelfRel());
//    }
}
