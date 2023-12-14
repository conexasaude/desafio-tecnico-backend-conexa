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

import com.felipe.mapper.DoctorMapper;
import com.felipe.model.Doctor;
import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.repositories.DoctorRepository;
import com.felipe.unittests.mapper.mocks.MockDoctor;

@SpringBootTest
//@DataJdbcTest
class DoctorRepositoryTest {

	private Logger logger = Logger.getLogger(DoctorRepositoryTest.class.getName());

	@Autowired
	private DoctorRepository repository;

	@Autowired
	private DoctorMapper mapper;

	private Doctor doctor;
	private MockDoctor inputObject = new MockDoctor();

	@BeforeEach
	public void setUp() {
		doctor = inputObject.mockRandomEntity(false);
	}

	@DisplayName("Given Doctor Object when Save then Return Saved Doctor")
	@Test
	void testGivenDoctorObject_whenSave_thenReturnSavedDoctor() {
		logger.info("Given Doctor Object when Save then Return Saved Doctor");

		Doctor createdDoctor = repository.save(doctor);
		logger.info(createdDoctor.toString());

		DoctorDTO dto = mapper.toDto(createdDoctor);
		logger.info("DTO => " + dto.toString());

		assertNotNull(createdDoctor);
		assertNotNull(createdDoctor.getId());
	}

	@DisplayName("Given Doctor List when findAll then Return Doctor List")
	@Test
	void testGivenDoctorList_whenFindAll_thenReturnDoctorList() {

		List<Doctor> doctorList = repository.findAll();

		assertNotNull(doctorList);
		assertFalse(doctorList.isEmpty());
	}

	@DisplayName("Given Doctor Object when findByID then Return Doctor Object")
	@Test
	void testGivenDoctorObject_whenFindByID_thenReturnDoctorObject() {
		logger.info("Given Doctor Object when findByID then Return Doctor Object");

		logger.info(doctor.toString());

		Doctor createdDoctor = repository.save(doctor);
		logger.info(createdDoctor.toString());

		// buscar pelo uuid gerado pelo banco, pois id "settado" é sobrescrito pela
		// geração de uuid db
		Doctor doctorFound = repository.findById(createdDoctor.getId()).get();

		assertNotNull(doctorFound);
		assertEquals(doctor.getId(), doctorFound.getId());
	}

	@DisplayName("Given Doctor Object when findByEmail then Return Doctor Object")
	@Test
	void testGivenDoctorObject_whenFindByEmail_thenReturnDoctorObject() {
		logger.info("Given Doctor Object when findByEmail then Return Doctor Object");

		logger.info(doctor.toString());

		Doctor createdDoctor = repository.save(doctor);
		logger.info(createdDoctor.toString());

		Doctor doctorFound = repository.findByEmail(doctor.getEmail()).get();

		assertNotNull(doctorFound);
		assertEquals(doctor.getId(), doctorFound.getId());
	}

	@DisplayName("Given Doctor Object when findByCpf then Return Doctor Object")
	@Test
	void testGivenDoctorObject_whenFindByCpf_thenReturnDoctorObject() {
		logger.info("Given Doctor Object when findByCpf then Return Doctor Object");

		logger.info(doctor.toString());

		Doctor createdDoctor = repository.save(doctor);
		logger.info(createdDoctor.toString());

		Doctor doctorFound = repository.findByCpf(doctor.getCpf()).get();

		assertNotNull(doctorFound);
		assertEquals(doctor.getId(), doctorFound.getId());
	}

	@DisplayName("Given Doctor Object when Update Doctor then Return Update Doctor Object")
	@Test
	void testGivenDoctorObject_whenUpdateDoctorReturnUpdateDoctorObject() {
		logger.info("Given Doctor Object when Update Doctor then Return Update Doctor Object");

		logger.info(doctor.toString());

		Doctor createdDoctor = repository.save(doctor);
		logger.info(createdDoctor.toString());

		Doctor doctorFound = repository.findById(createdDoctor.getId()).get();
		doctorFound.setFullName(doctor.getFullName() + " Updated Name");
		doctorFound.setEmail(doctor.getEmail() + ".teste");
		logger.info(doctorFound.toString());

		Doctor updatedDoctor = repository.save(doctorFound);

		assertNotNull(updatedDoctor);
		assertEquals(doctor.getFullName() + " Updated Name", updatedDoctor.getFullName());
		assertEquals(doctor.getEmail() + ".teste", updatedDoctor.getEmail());
	}

	@DisplayName("Given Doctor Object when Delete then Remove Doctor")
	@Test
	void testGivenDoctorObject_whenDelete_thenRemoveDoctor() {
		logger.info("Given Doctor Object when Delete then Remove Doctor");

		logger.info(doctor.toString());

		Doctor createdDoctor = repository.save(doctor);
		logger.info(createdDoctor.toString());

		repository.deleteById(createdDoctor.getId());

		Optional<Doctor> doctorOptional = repository.findById(createdDoctor.getId());

		assertTrue(doctorOptional.isEmpty());
	}

	@DisplayName("Given FullName And Specialt when findByFullNameAndSpecialty then Return Doctor Object")
	@Test
	void testGivenFullNameAndSpecialt_whenFindByFullNameAndSpecialty_thenReturnDoctorObject() {
		logger.info("Given FullName And Specialt when findByFullNameAndSpecialty then Return Doctor Object");

		logger.info(doctor.toString());

		Doctor createdDoctor = repository.save(doctor);
		logger.info(createdDoctor.toString());

		Doctor doctorFound = repository.findByFullNameAndSpecialty(doctor.getFullName(), doctor.getSpecialty());

		assertNotNull(doctorFound);
		assertEquals(doctor.getFullName(), doctorFound.getFullName());
		assertEquals(doctor.getSpecialty(), doctorFound.getSpecialty());
	}
}
