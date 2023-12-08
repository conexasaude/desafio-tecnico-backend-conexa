package com.felipe.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.felipe.mapper.DoctorMapper;
import com.felipe.model.Doctor;
import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.unittests.mapper.mocks.MockDoctor;
import com.felipe.util.DateUtil;

public class DoctorMapperTest {
	MockDoctor inputObject;
	DoctorMapper doctorMapper = new DoctorMapper();

	@BeforeEach
	public void setUp() {
		inputObject = new MockDoctor();
	}

	@Test
	public void parseEntityToDTOTest() {
		DoctorDTO output = doctorMapper.toDto(inputObject.mockEntity());

		assertEquals("doctor.email0@gmail.com", output.getEmail());
		assertEquals("Full Name Test0", output.getFullName());
		assertEquals("100.200.300-40", output.getCpf());
		assertEquals("21/05/1980", output.getBirthDate());
		assertEquals("(21) 3232-6560", output.getPhone());
		assertEquals("Pediatra0", output.getSpecialty());
	}

	@Test
	public void parseEntityListToDTOListTest() {
		List<DoctorDTO> outputList = doctorMapper.toDto(inputObject.mockEntityList());

		DoctorDTO outputZero = outputList.get(0);

		assertEquals("doctor.email0@gmail.com", outputZero.getEmail());
		assertEquals("Full Name Test0", outputZero.getFullName());
		assertEquals("100.200.300-40", outputZero.getCpf());
		assertEquals("21/05/1980", outputZero.getBirthDate());
		assertEquals("(21) 3232-6560", outputZero.getPhone());
		assertEquals("Pediatra0", outputZero.getSpecialty());
		DoctorDTO outputFour = outputList.get(4);

		assertEquals("doctor.email4@gmail.com", outputFour.getEmail());
		assertEquals("Full Name Test4", outputFour.getFullName());
		assertEquals("100.200.300-44", outputFour.getCpf());
		assertEquals("21/05/1984", outputFour.getBirthDate());
		assertEquals("(21) 3232-6564", outputFour.getPhone());
		assertEquals("Pediatra4", outputFour.getSpecialty());

		DoctorDTO outputSeven = outputList.get(7);

		assertEquals("doctor.email7@gmail.com", outputSeven.getEmail());
		assertEquals("Full Name Test7", outputSeven.getFullName());
		assertEquals("100.200.300-47", outputSeven.getCpf());
		assertEquals("21/05/1987", outputSeven.getBirthDate());
		assertEquals("(21) 3232-6567", outputSeven.getPhone());
		assertEquals("Pediatra7", outputSeven.getSpecialty());
	}

	@Test
	public void parseDTOToEntityTest() {
		Doctor output = doctorMapper.toEntity(inputObject.mockDTO());

		assertEquals("doctor.email0@gmail.com", output.getEmail());
		assertEquals("Full Name Test0", output.getFullName());
		assertEquals("100.200.300-40", output.getCpf());
		assertEquals(DateUtil.convertStringToLocalDate("21/05/1980"), output.getBirthDate());
		assertEquals("(21) 3232-6560", output.getPhone());
		assertEquals("Pediatra0", output.getSpecialty());
	}

	@Test
	public void parserDTOListToEntityListTest() {
		List<Doctor> outputList = doctorMapper.toEntity(inputObject.mockDTOList());

		Doctor outputZero = outputList.get(0);

		assertEquals("doctor.email0@gmail.com", outputZero.getEmail());
		assertEquals("Full Name Test0", outputZero.getFullName());
		assertEquals("100.200.300-40", outputZero.getCpf());
		assertEquals(DateUtil.convertStringToLocalDate("21/05/1980"), outputZero.getBirthDate());
		assertEquals("(21) 3232-6560", outputZero.getPhone());
		assertEquals("Pediatra0", outputZero.getSpecialty());

		Doctor outputFour = outputList.get(4);

		assertEquals("doctor.email4@gmail.com", outputFour.getEmail());
		assertEquals("Full Name Test4", outputFour.getFullName());
		assertEquals("100.200.300-44", outputFour.getCpf());
		assertEquals(DateUtil.convertStringToLocalDate("21/05/1984"), outputFour.getBirthDate());
		assertEquals("(21) 3232-6564", outputFour.getPhone());
		assertEquals("Pediatra4", outputFour.getSpecialty());

		Doctor outputSeven = outputList.get(7);

		assertEquals("doctor.email7@gmail.com", outputSeven.getEmail());
		assertEquals("Full Name Test7", outputSeven.getFullName());
		assertEquals("100.200.300-47", outputSeven.getCpf());
		assertEquals(DateUtil.convertStringToLocalDate("21/05/1987"), outputSeven.getBirthDate());
		assertEquals("(21) 3232-6567", outputSeven.getPhone());
		assertEquals("Pediatra7", outputSeven.getSpecialty());
	}
}
