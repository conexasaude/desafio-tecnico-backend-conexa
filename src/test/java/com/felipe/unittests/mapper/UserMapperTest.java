package com.felipe.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.felipe.mapper.UserMapper;
import com.felipe.model.User;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.unittests.mapper.mocks.MockUser;
import com.felipe.util.DateUtil;

public class UserMapperTest {
    MockUser inputObject;
    UserMapper userMapper = new UserMapper();
    
    @BeforeEach
    public void setUp() {
        inputObject = new MockUser();
    }

    @Test
    public void parseEntityToDTOTest() {
        UserDTO output = userMapper.toDto(inputObject.mockEntity());

        assertEquals("user.email0@gmail.com", output.getEmail());
        assertEquals("Full Name Test0", output.getFullName());
        assertEquals("password010", output.getPassword());
        assertEquals("Speciality Test0", output.getSpecialty());
        assertEquals("100.200.300-40", output.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1980"), output.getBirthDate());
        assertEquals("(21) 3232-6560", output.getPhone());
    }

    @Test
    public void parseEntityListToDTOListTest() {
    	List<UserDTO> outputList = userMapper.toDto(inputObject.mockEntityList());

        UserDTO outputZero = outputList.get(0);
        
        assertEquals("user.email0@gmail.com", outputZero.getEmail());
        assertEquals("Full Name Test0", outputZero.getFullName());
        assertEquals("password010", outputZero.getPassword());
        assertEquals("Speciality Test0", outputZero.getSpecialty());
        assertEquals("100.200.300-40", outputZero.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1980"), outputZero.getBirthDate());
        assertEquals("(21) 3232-6560", outputZero.getPhone());
        
        UserDTO outputFour= outputList.get(4);
        
        assertEquals("user.email4@gmail.com", outputFour.getEmail());
        assertEquals("Full Name Test4", outputFour.getFullName());
        assertEquals("password014", outputFour.getPassword());
        assertEquals("Speciality Test4", outputFour.getSpecialty());
        assertEquals("100.200.300-44", outputFour.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1984"), outputFour.getBirthDate());
        assertEquals("(21) 3232-6564", outputFour.getPhone());
        
        UserDTO outputSeven= outputList.get(7);
        
        assertEquals("user.email7@gmail.com", outputSeven.getEmail());
        assertEquals("Full Name Test7", outputSeven.getFullName());
        assertEquals("password017", outputSeven.getPassword());
        assertEquals("Speciality Test7", outputSeven.getSpecialty());
        assertEquals("100.200.300-47", outputSeven.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1987"), outputSeven.getBirthDate());
        assertEquals("(21) 3232-6567", outputSeven.getPhone());
    }

    @Test
    public void parseDTOToEntityTest() {
    	User output = userMapper.toEntity(inputObject.mockDTO());

        assertEquals("user.email0@gmail.com", output.getEmail());
        assertEquals("Full Name Test0", output.getFullName());
        assertEquals("password010", output.getPassword());
        assertEquals("Speciality Test0", output.getSpecialty());
        assertEquals("100.200.300-40", output.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1980"), output.getBirthDate());
        assertEquals("(21) 3232-6560", output.getPhone());
    }

    @Test
    public void parserDTOListToEntityListTest() {
    	List<User> outputList = userMapper.toEntity(inputObject.mockDTOList());

        User outputZero = outputList.get(0);
        
        assertEquals("user.email0@gmail.com", outputZero.getEmail());
        assertEquals("Full Name Test0", outputZero.getFullName());
        assertEquals("password010", outputZero.getPassword());
        assertEquals("Speciality Test0", outputZero.getSpecialty());
        assertEquals("100.200.300-40", outputZero.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1980"), outputZero.getBirthDate());
        assertEquals("(21) 3232-6560", outputZero.getPhone());
        
        User outputFour= outputList.get(4);
        
        assertEquals("user.email4@gmail.com", outputFour.getEmail());
        assertEquals("Full Name Test4", outputFour.getFullName());
        assertEquals("password014", outputFour.getPassword());
        assertEquals("Speciality Test4", outputFour.getSpecialty());
        assertEquals("100.200.300-44", outputFour.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1984"), outputFour.getBirthDate());
        assertEquals("(21) 3232-6564", outputFour.getPhone());
        
        User outputSeven= outputList.get(7);
        
        assertEquals("user.email7@gmail.com", outputSeven.getEmail());
        assertEquals("Full Name Test7", outputSeven.getFullName());
        assertEquals("password017", outputSeven.getPassword());
        assertEquals("Speciality Test7", outputSeven.getSpecialty());
        assertEquals("100.200.300-47", outputSeven.getCpf());
        assertEquals(DateUtil.convertStringToLocalDate("21/05/1987"), outputSeven.getBirthDate());
        assertEquals("(21) 3232-6567", outputSeven.getPhone());

    }
}
