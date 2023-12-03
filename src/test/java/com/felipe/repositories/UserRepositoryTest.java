package com.felipe.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.felipe.model.User;
import com.felipe.service.UserService;
import com.felipe.unittests.mapper.mocks.MockUser;
import com.felipe.util.DateUtil;
import com.felipe.utils.EmailGeneretor;
import com.felipe.utils.documents.GenerateDocument;
import com.github.javafaker.Faker;

@SpringBootTest
//@DataJdbcTest
class UserRepositoryTest {

	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	private UserRepository repository;

	private GenerateDocument generateDocument = new GenerateDocument();

	private Faker faker = new Faker();

	MockUser inputObject;

	@BeforeEach
	public void setUp() {
		inputObject = new MockUser();
	}

	@DisplayName("Given User Object when Save then Return Saved User")
	@Test
	void testGivenUserObject_whenSave_thenReturnSavedUser() {
		User input = inputObject.mockEntity();
		input.setCpf(generateDocument.cpf(true));
		input.setEmail(EmailGeneretor.generateEmail(input.getFullName()));
		User output = repository.save(input);

		logger.info(output.toString());

		assertNotNull(output);
		assertNotNull(output.getId());
	}

	@DisplayName("Given User List when findAll then Return User List")
	@Test
	void testGivenUserList_whenFindAll_thenReturnUserList() {

		List<User> userList = repository.findAll();

		assertNotNull(userList);
		assertFalse(userList.isEmpty());
	}

	@DisplayName("Given User Object when findByID then Return User Object")
	@Test
	void testGivenUserObject_whenFindByID_thenReturnUserObject() {

		String fullname = faker.name().fullName();
		String email = EmailGeneretor.generateEmail(fullname);
		String cpf = generateDocument.cpf(true);
		String phone = faker.phoneNumber().phoneNumber();
		User user = new User(email, fullname, "senha123", "Pediatra", cpf,
				DateUtil.convertStringToLocalDate("21/05/1981"), phone);
		logger.info(user.toString());

		User createdUser = repository.save(user);

		logger.info(createdUser.toString());

		// buscar pelo uuid gerado pelo banco, pois id "settado" é sobrescrito pela geração de uuid db
		User userFound = repository.findById(createdUser.getId()).get();

		assertNotNull(userFound);
		assertEquals(user.getId(), userFound.getId());
	}

	@DisplayName("Given User Object when findByEmail then Return User Object")
	@Test
	void testGivenUserObject_whenFindByEmail_thenReturnUserObject() {

		String fullname = faker.name().fullName();
		String email = EmailGeneretor.generateEmail(fullname);
		String cpf = generateDocument.cpf(true);
		String phone = faker.phoneNumber().phoneNumber();
		User user = new User(email, fullname, "senha123", "Pediatra", cpf,
				DateUtil.convertStringToLocalDate("21/05/1981"), phone);
		logger.info(user.toString());

		User createdUser = repository.save(user);

		logger.info(createdUser.toString());

		User userFound = repository.findByEmail(user.getEmail()).get();

		assertNotNull(userFound);
		assertEquals(user.getId(), userFound.getId());
	}
}
