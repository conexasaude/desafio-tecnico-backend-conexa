package com.felipe.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.felipe.model.User;
import com.felipe.service.UserService;
import com.felipe.unittests.mapper.mocks.MockUser;
import com.felipe.utils.CPFGeneretor;
import com.felipe.utils.EmailGeneretor;


@SpringBootTest
//@DataJdbcTest
class UserRepositoryTest {
	
	private Logger logger = Logger.getLogger(UserService.class.getName());
	
	@Autowired
	private UserRepository repository;
	
	MockUser inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockUser();
    }

	
	@DisplayName("Given User Object when Save then Return Saved User")
	@Test
	void testGivenUserObject_whenSave_thenReturnSavedUser() {
        User input = inputObject.mockEntity();
        input.setCpf(CPFGeneretor.generateCPF());
        input.setEmail(EmailGeneretor.generateEmail(input.getFullName()));
        User output = repository.save(input);
        
		logger.info(output.toString());

        System.out.println(output.toString());
        assertNotNull(output);
        assertNotNull(output.getId());

	}

}
