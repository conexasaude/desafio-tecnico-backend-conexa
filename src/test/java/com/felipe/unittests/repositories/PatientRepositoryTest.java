package com.felipe.unittests.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.felipe.mapper.PatientMapper;
import com.felipe.model.Patient;
import com.felipe.model.dto.v1.PatientDTO;
import com.felipe.repositories.PatientRepository;
import com.felipe.service.PatientService;
import com.felipe.unittests.mapper.mocks.MockPatient;

@SpringBootTest
//@DataJdbcTest
class PatientRepositoryTest {

	private Logger logger = Logger.getLogger(PatientService.class.getName());

	@Autowired
	private PatientRepository repository;

	@Autowired
	private PatientMapper mapper;
	
	private Patient patient;
	private MockPatient inputObject = new MockPatient();

	@BeforeEach
	public void setUp() {
		patient = inputObject.mockRandomEntity(false);
	}

	@DisplayName("Given Patient Object when Save then Return Saved Patient")
	@Test
	void testGivenPatientObject_whenSave_thenReturnSavedPatient() {
		logger.info("Given Patient Object when Save then Return Saved Patient");

		Patient createdPatient = repository.save(patient);
		logger.info(createdPatient.toString());

		PatientDTO dto = mapper.toDto(createdPatient);
		logger.info("DTO => " + dto.toString());

		assertNotNull(createdPatient);
		assertNotNull(createdPatient.getId());
	}

	@DisplayName("Given Patient List when findAll then Return Patient List")
	@Test
	void testGivenPatientList_whenFindAll_thenReturnPatientList() {

		List<Patient> patientList = repository.findAll();

		assertNotNull(patientList);
		assertFalse(patientList.isEmpty());
	}

	@DisplayName("Given Patient Object when findByID then Return Patient Object")
	@Test
	void testGivenPatientObject_whenFindByID_thenReturnPatientObject() {
		logger.info("Given Patient Object when findByID then Return Patient Object");

		logger.info(patient.toString());

		Patient createdPatient = repository.save(patient);
		logger.info(createdPatient.toString());

		// buscar pelo uuid gerado pelo banco, pois id "settado" é sobrescrito pela
		// geração de uuid db
		Patient patientFound = repository.findById(createdPatient.getId()).get();

		assertNotNull(patientFound);
		assertEquals(patient.getId(), patientFound.getId());
	}

	@DisplayName("Given Patient Object when findByEmail then Return Patient Object")
	@Test
	void testGivenPatientObject_whenFindByEmail_thenReturnPatientObject() {
		logger.info("Given Patient Object when findByEmail then Return Patient Object");

		logger.info(patient.toString());

		Patient createdPatient = repository.save(patient);
		logger.info(createdPatient.toString());

		Patient patientFound = repository.findByEmail(patient.getEmail()).get();

		assertNotNull(patientFound);
		assertEquals(patient.getId(), patientFound.getId());
	}
	
	@DisplayName("Given Patient Object when findByCpf then Return Patient Object")
	@Test
	void testGivenPatientObject_whenFindByCpf_thenReturnPatientObject() {
		logger.info("Given Patient Object when findByCpf then Return Patient Object");

		logger.info(patient.toString());

		Patient createdPatient = repository.save(patient);
		logger.info(createdPatient.toString());

		Patient patientFound = repository.findByCpf(patient.getCpf()).get();

		assertNotNull(patientFound);
		assertEquals(patient.getId(), patientFound.getId());
	}

	@DisplayName("Given Patient Object when Update Patient then Return Update Patient Object")
	@Test
	void testGivenPatientObject_whenUpdatePatientReturnUpdatePatientObject() {
		logger.info("Given Patient Object when Update Patient then Return Update Patient Object");

		logger.info(patient.toString());

		Patient createdPatient = repository.save(patient);
		logger.info(createdPatient.toString());

		Patient patientFound = repository.findById(createdPatient.getId()).get();
		patientFound.setFullName(patient.getFullName() + " Updated Name");
		patientFound.setEmail(patient.getEmail() + ".teste");
		logger.info(patientFound.toString());

		Patient updatedPatient = repository.save(patientFound);

		assertNotNull(updatedPatient);
		assertEquals(patient.getFullName() + " Updated Name", updatedPatient.getFullName());
		assertEquals(patient.getEmail() + ".teste", updatedPatient.getEmail());
	}

	@DisplayName("Given Patient Object when Delete then Remove Patient")
	@Test
	void testGivenPatientObject_whenDelete_thenRemovePatient() {
		logger.info("Given Patient Object when Delete then Remove Patient");

		logger.info(patient.toString());

		Patient createdPatient = repository.save(patient);
		logger.info(createdPatient.toString());

		repository.deleteById(createdPatient.getId());

		Optional<Patient> patientOptional = repository.findById(createdPatient.getId());

		assertTrue(patientOptional.isEmpty());
	}
	
}
