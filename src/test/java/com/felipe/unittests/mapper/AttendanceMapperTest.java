package com.felipe.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.felipe.mapper.AttendanceMapper;
import com.felipe.model.Attendance;
import com.felipe.model.dto.v1.AttendanceDTO;
import com.felipe.unittests.mapper.mocks.MockAttendance;
import com.felipe.util.DateUtil;

@SpringBootTest
public class AttendanceMapperTest {
    MockAttendance inputObject;
    
    @Autowired
    AttendanceMapper attendanceMapper;

    @BeforeEach
    public void setUp() {
        inputObject = new MockAttendance();
    }

    @Test
    public void parseEntityToDTOTest() {
    	LocalDateTime dateTime = LocalDateTime.now().plusHours(7);
    	Attendance attendance = inputObject.mockEntity(dateTime);
        AttendanceDTO output = attendanceMapper.toDto(attendance);

        assertEquals(DateUtil.convertLocalDateTimeToString(dateTime), output.getDateTime());
        assertEquals("Full Name Test0", output.getPatient().getFullName());
        assertEquals("100.200.300-40", output.getPatient().getCpf());
        assertEquals("SulAmericano0", output.getPatient().getHealthInsurance());
    }

    @Test
    public void parseEntityListToDTOListTest() {
    	LocalDateTime dateTime = LocalDateTime.now().plusHours(7);
    	List<AttendanceDTO> outputList = attendanceMapper.toDto(inputObject.mockEntityList(dateTime));

        AttendanceDTO outputZero = outputList.get(0);

        assertEquals("Full Name Test0", outputZero.getPatient().getFullName());
        assertEquals("100.200.300-40", outputZero.getPatient().getCpf());
        assertEquals("SulAmericano0", outputZero.getPatient().getHealthInsurance());
        assertEquals(DateUtil.convertLocalDateTimeToString(dateTime), outputZero.getDateTime());

        AttendanceDTO outputFour= outputList.get(4);

        assertEquals("Full Name Test4", outputFour.getPatient().getFullName());
        assertEquals("100.200.300-44", outputFour.getPatient().getCpf());
        assertEquals("SulAmericano4", outputFour.getPatient().getHealthInsurance());
        assertEquals(DateUtil.convertLocalDateTimeToString(dateTime), outputFour.getDateTime());

        AttendanceDTO outputSeven= outputList.get(7);

        assertEquals("Full Name Test7", outputSeven.getPatient().getFullName());
        assertEquals("100.200.300-47", outputSeven.getPatient().getCpf());
        assertEquals("SulAmericano7", outputSeven.getPatient().getHealthInsurance());
        assertEquals(DateUtil.convertLocalDateTimeToString(dateTime), outputSeven.getDateTime());

    }

    @Test
    public void parseDTOToEntityTest() {
    	LocalDateTime dateTime = LocalDateTime.now().plusHours(7);

    	Attendance output = attendanceMapper.toEntity(inputObject.mockDTO());

        assertEquals(dateTime.withNano(0), output.getDateTime());
        assertEquals("Full Name Test0", output.getPatient().getFullName());
        assertEquals("100.200.300-40", output.getPatient().getCpf());
        assertEquals("SulAmericano0", output.getPatient().getHealthInsurance());

    }

    @Test
    public void parserDTOListToEntityListTest() {
    	LocalDateTime dateTime = LocalDateTime.now().plusHours(7);
    	List<Attendance> outputList = attendanceMapper.toEntity(inputObject.mockDTOList());

        Attendance outputZero = outputList.get(0);

        assertEquals("Full Name Test0", outputZero.getPatient().getFullName());
        assertEquals("100.200.300-40", outputZero.getPatient().getCpf());
        assertEquals("SulAmericano0", outputZero.getPatient().getHealthInsurance());
        assertEquals(dateTime.withNano(0), outputZero.getDateTime());

        Attendance outputFour= outputList.get(4);

        assertEquals("Full Name Test4", outputFour.getPatient().getFullName());
        assertEquals("100.200.300-44", outputFour.getPatient().getCpf());
        assertEquals("SulAmericano4", outputFour.getPatient().getHealthInsurance());
        assertEquals(dateTime.withNano(0), outputFour.getDateTime());

        Attendance outputSeven= outputList.get(7);

        assertEquals("Full Name Test7", outputSeven.getPatient().getFullName());
        assertEquals("100.200.300-47", outputSeven.getPatient().getCpf());
        assertEquals("SulAmericano7", outputSeven.getPatient().getHealthInsurance());
        assertEquals(dateTime.withNano(0), outputSeven.getDateTime());
    }
}
