package com.felipe.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import com.felipe.exceptions.BadRequestException;
import com.felipe.mapper.DoctorMapper;
import com.felipe.model.Doctor;
import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.repositories.DoctorRepository;
import com.felipe.service.DoctorService;
import com.felipe.service.PatientService;
import com.felipe.unittests.mapper.mocks.MockDoctor;
import com.felipe.util.MessageUtils;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

	private Logger logger = Logger.getLogger(PatientService.class.getName());

	@Mock
	private DoctorRepository repository;

//	@Autowired
	private DoctorMapper mapper = new DoctorMapper();

	@Autowired
	@InjectMocks
	private DoctorService service;

	private DoctorDTO doctorDto;

	private Doctor doctor;

	MockDoctor inputObject = new MockDoctor();
	Pageable pageable;

	@BeforeEach
	public void setUp() {
		pageable = PageRequest.of(0, 12, Sort.by(Direction.ASC, "fullName"));

		doctor = inputObject.mockRandomEntity(true);
		doctorDto = mapper.toDto(doctor);
	}

	@DisplayName("JUnit test for Given Doctors Object when Save Doctor then Return Doctor Object")
	@Test
	void testGivenDoctorObject_whenSaveDoctor_thenReturnDoctorObject() throws Exception {
		logger.info("JUnit test for Given Doctors Object when Save Doctor then Return Doctor Object");

		given(repository.findByEmail(anyString())).willReturn(Optional.empty());
		given(repository.save(any(Doctor.class))).willReturn(doctor);

		logger.info(doctor.toString());
		logger.info("DTO => " + doctorDto.toString());

		DoctorDTO doctorDtoCreated = service.create(doctorDto);
		logger.info("CREATED => " + doctorDtoCreated.toString());

		assertNotNull(doctorDtoCreated);
		assertTrue(!doctorDtoCreated.getKey().toString().isEmpty());
		assertEquals(doctor.getFullName(), doctorDtoCreated.getFullName());

	}

	@DisplayName("JUnit test for Given Existing Email when Save Doctor then Throws Exception")
	@Test
	void testGivenExistingEmail_whenSaveDoctor_thenThrowsException() throws Exception {
		logger.info("JUnit test for Given Existing Emai when Save Doctor then Throws Exception");

		// Set the email of the DoctorDTO to "songpagaciv.2961@example.com"
		doctorDto.setEmail("songpagaciv.2961@example.com");

		// Mock the behavior of the repository
		given(repository.findByEmail("songpagaciv.2961@example.com")).willReturn(Optional.of(doctor));

		BadRequestException exception = assertThrows(BadRequestException.class, () -> {
			logger.info("Before service.create");
			service.create(doctorDto);
			logger.info("After service.create");
		});

		assertEquals("Email " + MessageUtils.RECORDS_ALREADY_EXIST + ": " + doctorDto.getEmail(), exception.getMessage());
		verify(repository, never()).save(any(Doctor.class));
	}

	@DisplayName("JUnit test for Given Existing CPF when Save Patient then Throws Exception")
	@Test
	void testGivenExistingCPF_whenSavePatient_thenThrowsException() throws Exception {
		logger.info("JUnit test for Given Existing CPF when Save Patient then Throws Exception");

		doctorDto.setCpf("085.491.221-25");

		// Mock the behavior of the repository
		given(repository.findByCpf("085.491.221-25")).willReturn(Optional.of(doctor));

		BadRequestException exception = assertThrows(BadRequestException.class, () -> {
			logger.info("Before service.create");
			service.create(doctorDto);
			logger.info("After service.create");
		});

		assertEquals("CPF " + MessageUtils.RECORDS_ALREADY_EXIST + ": " + doctorDto.getCpf(), exception.getMessage());
		verify(repository, never()).save(any(Doctor.class));
	}

	@DisplayName("JUnit test for Given Doctors List when Find All Doctors Then Return Doctors List")
	@Test
	void testGivenDoctorsList_whenFindAllDoctors_thenReturnDoctorsList() throws Exception  {
		logger.info("JUnit test for Given Doctors List when Find All Doctors Then Return Doctors List");

	    List<Doctor> doctorList = inputObject.mockRandomEntityList(3, true);
	    Page<Doctor> page = new PageImpl<>(doctorList, pageable, doctorList.size());

	    // Mock the behavior of the repository
	    given(repository.findAll(pageable)).willReturn(page);

		PagedModel<EntityModel<DoctorDTO>> doctorsDtoList = service.findAll(pageable);
		logger.info("SIZE => " + doctorsDtoList.getMetadata().getTotalElements());

		assertNotNull(doctorsDtoList);
		assertEquals(3, doctorsDtoList.getMetadata().getTotalElements());
	}

	@DisplayName("JUnit test for Given Empty Doctors List when Find All Doctors Then Return Empty Doctors List")
	@Test
	void testGivenEmptyDoctorsList_whenFindAllDoctors_thenReturnEmptyDoctorsList() throws Exception {
		logger.info("JUnit test for Given Empty Doctors List when Find All Doctors Then Return Empty Doctors List");

		// Mock the behavior of the repository
	    given(repository.findAll(pageable)).willReturn(new PageImpl<>(Collections.emptyList(), pageable, 0));

		PagedModel<EntityModel<DoctorDTO>> doctorsDtoList = service.findAll(pageable);
		logger.info("SIZE => " + doctorsDtoList.getMetadata().getTotalElements());

		assertTrue(doctorsDtoList.getContent().isEmpty());
		assertEquals(0, doctorsDtoList.getMetadata().getTotalElements());
	}

	@DisplayName("JUnit test for Given DoctorId when Find By Id Then Return Doctor Object")
	@Test
	void testGivenDoctorsId_whenFindById_thenReturnDoctorObject() throws Exception {
		logger.info("JUnit test for Given DoctorId when Find By Id Then Return Doctor Object");

	    //UUID valid for mocks
	    String doctorIdString = UUID.randomUUID().toString();

		given(repository.findById(any(UUID.class))).willReturn(Optional.of(doctor));


		DoctorDTO doctorFound = service.findById(doctorIdString);
		logger.info("FoundByID => " + doctorDto.toString());
		logger.info("USER => " + doctor.toString());

		assertNotNull(doctorFound);
		assertTrue(!doctorFound.getKey().toString().isEmpty());
		assertEquals(doctor.getFullName(), doctorFound.getFullName());
	}

	@DisplayName("JUnit test for Given Doctor Object when Update Doctor Then Return Update Doctor Object")
	@Test
	void testGivenDoctorObject_whenUpdateDoctor_thenReturnUpdateDoctorObject() throws Exception {
		logger.info("JUnit test for Given Object when Update Doctor Then Return Update Doctor Object");

		given(repository.findById(any(UUID.class))).willReturn(Optional.of(doctor));
		given(repository.save(any(Doctor.class))).willReturn(doctor);

		String newName = "Mauricio Di Paula";
		String newEmail = "mauricio_dipaula@email.com";
		logger.info("Before Update => " + doctorDto.toString());

		doctor.setFullName(newName);
		doctor.setEmail(newEmail);
		doctorDto = mapper.toDto(doctor);

		DoctorDTO doctorUpdated = service.update(doctorDto);
		logger.info("After Update => " + doctorDto.toString());

		assertNotNull(doctorUpdated);
		assertEquals(newName, doctorUpdated.getFullName());
		assertEquals(newEmail, doctorUpdated.getEmail());
	}

	@DisplayName("JUnit test for Given DoctorID when Delete Doctor then do Nothing")
	@Test
	void testGivenDoctorID_whenDeleteDoctor_thenDoNothing() throws Exception {
		logger.info("JUnit test for Given DoctorID when Delete Doctor then do Nothing");

		given(repository.findById(any(UUID.class))).willReturn(Optional.of(doctor));
		willDoNothing().given(repository).delete(doctor);

		service.delete(doctor.getId().toString());

		verify(repository, times(1)).delete(doctor);
	}

}
