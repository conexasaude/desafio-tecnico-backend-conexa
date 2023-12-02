package com.felipe.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.felipe.model.User;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.util.DateUtil;

public class MockUser {
	
	public User mockEntity() {
		return mockEntity(0);
	}
	    
    public UserDTO mockDTO() {
        return mockDTO(0);
    }
	    
    public List<User> mockEntityList() {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 8; i++) {
        	users.add(mockEntity(i));
        }
        return users;
    }

    public List<UserDTO> mockDTOList() {
        List<UserDTO> users = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
        	users.add(mockDTO(i));
        }
        return users;
    }
	    
    public User mockEntity(Integer number) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user.email" + number + "@gmail.com");
        user.setFullName("Full Name Test" + number);
        user.setPassword("password01" + number);
        user.setSpecialty("Speciality Test" + number);
        user.setCpf("100.200.300-4" + number);
        user.setBirthDate(DateUtil.convertStringToLocalDate("21/05/198" + number));
        user.setPhone("(21) 3232-656" + number);
        return user;
    }

    public UserDTO mockDTO(Integer number) {
        UserDTO user = new UserDTO();
        user.setKey(UUID.randomUUID());
        user.setEmail("user.email" + number + "@gmail.com");
        user.setFullName("Full Name Test" + number);
        user.setPassword("password01" + number);
        user.setSpecialty("Speciality Test" + number);
        user.setCpf("100.200.300-4" + number);
        user.setBirthDate(DateUtil.convertStringToLocalDate("21/05/198" + number));
        user.setPhone("(21) 3232-656" + number);
        return user;
    }
}
