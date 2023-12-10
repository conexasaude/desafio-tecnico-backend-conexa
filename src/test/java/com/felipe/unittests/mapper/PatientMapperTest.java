package com.felipe.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.felipe.mapper.PatientMapper;
import com.felipe.model.Patient;
import com.felipe.model.dto.v1.PatientDTO;
import com.felipe.unittests.mapper.mocks.MockPatient;

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

        assertEquals("Full Name Test0", output.getFullName());
        assertEquals("100.200.300-40", output.getCpf());
        assertEquals("SulAmericano0", output.getHealthInsurance());
    }

    @Test
    public void parseEntityListToDTOListTest() {
    	List<PatientDTO> outputList = patientMapper.toDto(inputObject.mockEntityList());

        PatientDTO outputZero = outputList.get(0);
        
        assertEquals("Full Name Test0", outputZero.getFullName());
        assertEquals("100.200.300-40", outputZero.getCpf());
        assertEquals("SulAmericano0", outputZero.getHealthInsurance());

        PatientDTO outputFour= outputList.get(4);
        
        assertEquals("Full Name Test4", outputFour.getFullName());
        assertEquals("100.200.300-44", outputFour.getCpf());
        assertEquals("SulAmericano4", outputFour.getHealthInsurance());

        PatientDTO outputSeven= outputList.get(7);
        
        assertEquals("Full Name Test7", outputSeven.getFullName());
        assertEquals("100.200.300-47", outputSeven.getCpf());
        assertEquals("SulAmericano7", outputSeven.getHealthInsurance());
    }

    @Test
    public void parseDTOToEntityTest() {
    	Patient output = patientMapper.toEntity(inputObject.mockDTO());

        assertEquals("Full Name Test0", output.getFullName());
        assertEquals("100.200.300-40", output.getCpf());
        assertEquals("SulAmericano0", output.getHealthInsurance());

    }

    @Test
    public void parserDTOListToEntityListTest() {
    	List<Patient> outputList = patientMapper.toEntity(inputObject.mockDTOList());

        Patient outputZero = outputList.get(0);
        
        assertEquals("Full Name Test0", outputZero.getFullName());
        assertEquals("100.200.300-40", outputZero.getCpf());
        assertEquals("SulAmericano0", outputZero.getHealthInsurance());

        Patient outputFour= outputList.get(4);
        
        assertEquals("Full Name Test4", outputFour.getFullName());
        assertEquals("100.200.300-44", outputFour.getCpf());
        assertEquals("SulAmericano4", outputFour.getHealthInsurance());

        Patient outputSeven= outputList.get(7);
        
        assertEquals("Full Name Test7", outputSeven.getFullName());
        assertEquals("100.200.300-47", outputSeven.getCpf());
        assertEquals("SulAmericano7", outputSeven.getHealthInsurance());

    }
}
