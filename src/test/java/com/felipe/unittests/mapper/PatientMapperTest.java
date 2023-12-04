package com.felipe.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.felipe.mapper.PatientMapper;
import com.felipe.model.Patient;
import com.felipe.model.dto.v1.PatientDTO;
import com.felipe.unittests.mapper.mocks.MockPatient;
import com.felipe.util.DateUtil;

public class PatientMapperTest {
    MockPatient inputObject;
    PatientMapper patientMapper = new PatientMapper();
    
    @BeforeEach
    public void setUp() {
        inputObject = new MockPatient();
    }

    @Test
    public void parseEntityToDTOTest() {
        PatientDTO output = patientMapper.toDto(inputObject.mockEntity());

        assertEquals("patient.email0@gmail.com", output.getEmail());
        assertEquals("Full Name Test0", output.getFullName());
        assertEquals("password010", output.getPassword());
        assertEquals("100.200.300-40", output.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1980"), output.getBirthDate());
        assertEquals("(21) 3232-6560", output.getPhone());
        assertEquals("SulAmericano0", output.getHealthInsurance());
    }

    @Test
    public void parseEntityListToDTOListTest() {
    	List<PatientDTO> outputList = patientMapper.toDto(inputObject.mockEntityList());

        PatientDTO outputZero = outputList.get(0);
        
        assertEquals("patient.email0@gmail.com", outputZero.getEmail());
        assertEquals("Full Name Test0", outputZero.getFullName());
        assertEquals("password010", outputZero.getPassword());
        assertEquals("100.200.300-40", outputZero.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1980"), outputZero.getBirthDate());
        assertEquals("(21) 3232-6560", outputZero.getPhone());
        assertEquals("SulAmericano0", outputZero.getHealthInsurance());

        PatientDTO outputFour= outputList.get(4);
        
        assertEquals("patient.email4@gmail.com", outputFour.getEmail());
        assertEquals("Full Name Test4", outputFour.getFullName());
        assertEquals("password014", outputFour.getPassword());
        assertEquals("100.200.300-44", outputFour.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1984"), outputFour.getBirthDate());
        assertEquals("(21) 3232-6564", outputFour.getPhone());
        assertEquals("SulAmericano4", outputFour.getHealthInsurance());

        PatientDTO outputSeven= outputList.get(7);
        
        assertEquals("patient.email7@gmail.com", outputSeven.getEmail());
        assertEquals("Full Name Test7", outputSeven.getFullName());
        assertEquals("password017", outputSeven.getPassword());
        assertEquals("100.200.300-47", outputSeven.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1987"), outputSeven.getBirthDate());
        assertEquals("(21) 3232-6567", outputSeven.getPhone());
        assertEquals("SulAmericano7", outputSeven.getHealthInsurance());
    }

    @Test
    public void parseDTOToEntityTest() {
    	Patient output = patientMapper.toEntity(inputObject.mockDTO());

        assertEquals("patient.email0@gmail.com", output.getEmail());
        assertEquals("Full Name Test0", output.getFullName());
        assertEquals("password010", output.getPassword());
        assertEquals("100.200.300-40", output.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1980"), output.getBirthDate());
        assertEquals("(21) 3232-6560", output.getPhone());
        assertEquals("SulAmericano0", output.getHealthInsurance());

    }

    @Test
    public void parserDTOListToEntityListTest() {
    	List<Patient> outputList = patientMapper.toEntity(inputObject.mockDTOList());

        Patient outputZero = outputList.get(0);
        
        assertEquals("patient.email0@gmail.com", outputZero.getEmail());
        assertEquals("Full Name Test0", outputZero.getFullName());
        assertEquals("password010", outputZero.getPassword());
        assertEquals("100.200.300-40", outputZero.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1980"), outputZero.getBirthDate());
        assertEquals("(21) 3232-6560", outputZero.getPhone());
        assertEquals("SulAmericano0", outputZero.getHealthInsurance());

        Patient outputFour= outputList.get(4);
        
        assertEquals("patient.email4@gmail.com", outputFour.getEmail());
        assertEquals("Full Name Test4", outputFour.getFullName());
        assertEquals("password014", outputFour.getPassword());
        assertEquals("100.200.300-44", outputFour.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1984"), outputFour.getBirthDate());
        assertEquals("(21) 3232-6564", outputFour.getPhone());
        assertEquals("SulAmericano4", outputFour.getHealthInsurance());

        Patient outputSeven= outputList.get(7);
        
        assertEquals("patient.email7@gmail.com", outputSeven.getEmail());
        assertEquals("Full Name Test7", outputSeven.getFullName());
        assertEquals("password017", outputSeven.getPassword());
        assertEquals("100.200.300-47", outputSeven.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1987"), outputSeven.getBirthDate());
        assertEquals("(21) 3232-6567", outputSeven.getPhone());
        assertEquals("SulAmericano7", outputSeven.getHealthInsurance());

    }
}
