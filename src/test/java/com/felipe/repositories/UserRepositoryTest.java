package com.felipe.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.felipe.model.User;
import com.felipe.service.UserService;
import com.felipe.unittests.mapper.mocks.MockUser;

@SpringBootTest
//@DataJdbcTest
class UserRepositoryTest {

	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	private UserRepository repository;

	MockUser inputObject = new MockUser();;
	User user;

	@BeforeEach
	public void setUp() {
		user = inputObject.mockRandomEntity();
	}

	@DisplayName("Given User Object when Save then Return Saved User")
	@Test
	void testGivenUserObject_whenSave_thenReturnSavedUser() {
		logger.info("Given User Object when Save then Return Saved User");

		User createdUser = repository.save(user);

		logger.info(createdUser.toString());

		assertNotNull(createdUser);
		assertNotNull(createdUser.getId());
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
		logger.info("Given User Object when findByID then Return User Object");

		logger.info(user.toString());

		User createdUser = repository.save(user);
		logger.info(createdUser.toString());

		// buscar pelo uuid gerado pelo banco, pois id "settado" é sobrescrito pela
		// geração de uuid db
		User userFound = repository.findById(createdUser.getId()).get();

		assertNotNull(userFound);
		assertEquals(user.getId(), userFound.getId());
	}

	@DisplayName("Given User Object when findByEmail then Return User Object")
	@Test
	void testGivenUserObject_whenFindByEmail_thenReturnUserObject() {
		logger.info("Given User Object when findByEmail then Return User Object");

		logger.info(user.toString());

		User createdUser = repository.save(user);
		logger.info(createdUser.toString());

		User userFound = repository.findByEmail(user.getEmail()).get();

		assertNotNull(userFound);
		assertEquals(user.getId(), userFound.getId());
	}

	@DisplayName("Given User Object when Update User then Return Update User Object")
	@Test
	void testGivenUserObject_whenUpdateUserReturnUpdateUserObject() {
		logger.info("Given User Object when Update User then Return Update User Object");

		logger.info(user.toString());

		User createdUser = repository.save(user);
		logger.info(createdUser.toString());

		User userFound = repository.findById(createdUser.getId()).get();
		userFound.setFullName(user.getFullName() + " Updated Name");
		userFound.setEmail(user.getEmail() + ".teste");
		logger.info(userFound.toString());

		User updatedUser = repository.save(userFound);

		assertNotNull(updatedUser);
		assertEquals(user.getFullName() + " Updated Name", updatedUser.getFullName());
		assertEquals(user.getEmail() + ".teste", updatedUser.getEmail());
	}

	@DisplayName("Given User Object when Delete then Remove User")
	@Test
	void testGivenUserObject_whenDelete_thenRemoveUser() {
		logger.info("Given User Object when Delete then Remove User");

		logger.info(user.toString());

		User createdUser = repository.save(user);
		logger.info(createdUser.toString());

		repository.deleteById(createdUser.getId());

		Optional<User> userOptional = repository.findById(createdUser.getId());

		assertTrue(userOptional.isEmpty());
	}
	
	@DisplayName("Given FullName And Specialt when findByFullNameAndSpecialty then Return User Object")
	@Test
	void testGivenFullNameAndSpecialt_whenFindByFullNameAndSpecialty_thenReturnUserObject() {
		logger.info("Given FullName And Specialt when findByFullNameAndSpecialty then Return User Object");

		logger.info(user.toString());

		User createdUser = repository.save(user);
		logger.info(createdUser.toString());

		User userFound = repository.findByFullNameAndSpecialty(user.getFullName(), user.getSpecialty());

		assertNotNull(userFound);
		assertEquals(user.getFullName(), userFound.getFullName());
		assertEquals(user.getSpecialty(), userFound.getSpecialty());
	}
}
