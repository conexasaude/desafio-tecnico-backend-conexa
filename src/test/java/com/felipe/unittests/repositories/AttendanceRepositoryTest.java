package com.felipe.unittests.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.felipe.mapper.AttendanceMapper;
import com.felipe.model.Attendance;
import com.felipe.model.Patient;
import com.felipe.model.dto.v1.AttendanceDTO;
import com.felipe.repositories.AttendanceRepository;
import com.felipe.repositories.PatientRepository;
import com.felipe.unittests.mapper.mocks.MockAttendance;
import com.felipe.unittests.mapper.mocks.MockPatient;
import com.felipe.util.DateUtil;

@SpringBootTest
//@DataJdbcTest
class AttendanceRepositoryTest {

	private Logger logger = Logger.getLogger(AttendanceRepositoryTest.class.getName());

	@Autowired
	private AttendanceRepository repository;
	
	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AttendanceMapper mapper;

	private Attendance attendance;
	private Patient patient;
	
	
	private MockAttendance inputObject = new MockAttendance();
	private MockPatient inputObjectPatient = new MockPatient();

	@BeforeEach
	public void setUp() {
		attendance = inputObject.mockRandomEntity(false);
		patient = inputObjectPatient.mockRandomEntity(false);
	}

	@DisplayName("Given Attendance Object when Save then Return Saved Attendance")
	@Test
	void testGivenAttendanceObject_whenSave_thenReturnSavedAttendance() {
		logger.info("Given Attendance Object when Save then Return Saved Attendance");
		
		Patient createdPatient = patientRepository.save(patient);
		attendance.setPatient(createdPatient);
		
		Attendance createdAttendance = repository.save(attendance);
		logger.info(createdAttendance.toString());

		AttendanceDTO dto = mapper.toDto(createdAttendance);
		logger.info("DTO => " + dto.toString());

		assertNotNull(createdAttendance);
		assertNotNull(createdAttendance.getId());
	}

	@DisplayName("Given Attendance List when findAll then Return Attendance List")
	@Test
	void testGivenAttendanceList_whenFindAll_thenReturnAttendanceList() {

		List<Attendance> attendanceList = repository.findAll();

		assertNotNull(attendanceList);
		assertFalse(attendanceList.isEmpty());
	}

	@DisplayName("Given Attendance Object when findByID then Return Attendance Object")
	@Test
	void testGivenAttendanceObject_whenFindByID_thenReturnAttendanceObject() {
		logger.info("Given Attendance Object when findByID then Return Attendance Object");
		
		Patient createdPatient = patientRepository.save(patient);
		attendance.setPatient(createdPatient);

		logger.info(attendance.toString());

		Attendance createdAttendance = repository.save(attendance);
		logger.info(createdAttendance.toString());

		// buscar pelo uuid gerado pelo banco, pois id "settado" é sobrescrito pela
		// geração de uuid db
		Attendance attendanceFound = repository.findById(createdAttendance.getId()).get();

		assertNotNull(attendanceFound);
		assertEquals(attendance.getId(), attendanceFound.getId());
	}

//	@DisplayName("Given Attendance Object when findByEmail then Return Attendance Object")
//	@Test
//	void testGivenAttendanceObject_whenFindByEmail_thenReturnAttendanceObject() {
//		logger.info("Given Attendance Object when findByEmail then Return Attendance Object");
//
//		logger.info(attendance.toString());
//
//		Attendance createdAttendance = repository.save(attendance);
//		logger.info(createdAttendance.toString());
//
//		Attendance attendanceFound = repository.findByEmail(attendance.getEmail()).get();
//
//		assertNotNull(attendanceFound);
//		assertEquals(attendance.getId(), attendanceFound.getId());
//	}
//
//	@DisplayName("Given Attendance Object when findByCpf then Return Attendance Object")
//	@Test
//	void testGivenAttendanceObject_whenFindByCpf_thenReturnAttendanceObject() {
//		logger.info("Given Attendance Object when findByCpf then Return Attendance Object");
//
//		logger.info(attendance.toString());
//
//		Attendance createdAttendance = repository.save(attendance);
//		logger.info(createdAttendance.toString());
//
//		Attendance attendanceFound = repository.findByCpf(attendance.getCpf()).get();
//
//		assertNotNull(attendanceFound);
//		assertEquals(attendance.getId(), attendanceFound.getId());
//	}

	@DisplayName("Given Attendance Object when Update Attendance then Return Update Attendance Object")
	@Test
	void testGivenAttendanceObject_whenUpdateAttendanceReturnUpdateAttendanceObject() {
		logger.info("Given Attendance Object when Update Attendance then Return Update Attendance Object");
		
		Patient createdPatient = patientRepository.save(patient);
		attendance.setPatient(createdPatient);

		Attendance createdAttendance = repository.save(attendance);
//		logger.info(createdAttendance.toString());

		Attendance attendanceFound = repository.findById(createdAttendance.getId()).get();
		attendanceFound.setDateTime(LocalDateTime.now().plusHours(10));
//		logger.info(attendanceFound.toString());

		Attendance updatedAttendance = repository.save(attendanceFound);

		assertNotNull(updatedAttendance);
		assertEquals(LocalDateTime.now().plusHours(10).withNano(0), updatedAttendance.getDateTime().withNano(0));
	}

	@DisplayName("Given Attendance Object when Delete then Remove Attendance")
	@Test
	void testGivenAttendanceObject_whenDelete_thenRemoveAttendance() {
		logger.info("Given Attendance Object when Delete then Remove Attendance");

		logger.info(attendance.toString());
		
		Patient createdPatient = patientRepository.save(patient);
		attendance.setPatient(createdPatient);

		Attendance createdAttendance = repository.save(attendance);
		logger.info(createdAttendance.toString());

		repository.deleteById(createdAttendance.getId());

		Optional<Attendance> attendanceOptional = repository.findById(createdAttendance.getId());

		assertTrue(attendanceOptional.isEmpty());
	}

//	@DisplayName("Given FullName And Specialt when findByFullNameAndSpecialty then Return Attendance Object")
//	@Test
//	void testGivenFullNameAndSpecialt_whenFindByFullNameAndSpecialty_thenReturnAttendanceObject() {
//		logger.info("Given FullName And Specialt when findByFullNameAndSpecialty then Return Attendance Object");
//
//		logger.info(attendance.toString());
//
//		Attendance createdAttendance = repository.save(attendance);
//		logger.info(createdAttendance.toString());
//
//		Attendance attendanceFound = repository.findByFullNameAndSpecialty(attendance.getFullName(), attendance.getSpecialty());
//
//		assertNotNull(attendanceFound);
//		assertEquals(attendance.getFullName(), attendanceFound.getFullName());
//		assertEquals(attendance.getSpecialty(), attendanceFound.getSpecialty());
//	}
}
