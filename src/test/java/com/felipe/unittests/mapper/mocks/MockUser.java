package com.felipe.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.felipe.model.User;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.util.DateUtil;
import com.felipe.utils.EmailGeneretor;
import com.felipe.utils.documents.GenerateDocument;
import com.github.javafaker.Faker;

public class MockUser {
	
	private GenerateDocument generateDocument = new GenerateDocument();

	private Faker faker = new Faker();
	
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
	 
    public List<User> mockRandomEntityList(int quantityUsers, boolean withId) {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < quantityUsers; i++) {
        	users.add(mockRandomEntity(withId));
        }
        return users;
    }
    
    public User mockEntity(Integer number) {
        User user = new User();
//        user.setId(UUID.fromString("0c38ae25-0d29-43ad-91d4-73e91309267b"));
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
//        user.setKey(UUID.fromString("1bf6842a-06f6-480d-b766-6e2725e86007"));
        user.setEmail("user.email" + number + "@gmail.com");
        user.setFullName("Full Name Test" + number);
        user.setPassword("password01" + number);
        user.setSpecialty("Speciality Test" + number);
        user.setCpf("100.200.300-4" + number);
        user.setBirthDate(DateUtil.convertStringToLocalDate("21/05/198" + number));
        user.setPhone("(21) 3232-656" + number);
        return user;
    }
    
    public User mockRandomEntity(boolean withId) {
    	UUID id = withId ? UUID.randomUUID() : null;
		String fullname = faker.name().fullName();
		String email = EmailGeneretor.generateEmail(fullname);
		String cpf = generateDocument.cpf(true);
		String phone = faker.phoneNumber().phoneNumber();
		return new User(id, email, fullname, "senha123", "Pediatra", cpf,
				DateUtil.convertStringToLocalDate("21/05/1981"), phone);
    }
    
}
