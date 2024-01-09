package com.felipe.integrationtests.repositories;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.integrationtests.testcontainers.AbstractIntegrationTest;
import com.felipe.model.User;
import com.felipe.repositories.UserRepository;
import com.felipe.unittests.mapper.mocks.MockUser;
import com.felipe.util.MessageUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTest extends AbstractIntegrationTest {
	private Logger logger = Logger.getLogger(UserRepositoryTest.class.getName());

	@Autowired
	public UserRepository repository;

	private static User user;
	private static MockUser inputObject = new MockUser();

	@BeforeAll
	public static void setup() {
		user = new User();
		user = inputObject.mockRandomEntity(false);

	}

	@Test
	@Order(0)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		logger.info("testCreate=> ");

		User createdUser = repository.save(user);
		user = createdUser;
		logger.info(createdUser.toString());
	}

	@Test
	@Order(1)
	public void testFindByEmail() throws JsonMappingException, JsonProcessingException {
		logger.info("testFindByEmail => " + "   /api/v1/user/email/{email}");
		String username = user.getUsername();

		User entity = repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(
				MessageUtils.NO_RECORDS_FOUND + ": Email " + username + " not found!"));
		logger.info("testFindByEmail => " + user.toString());

		assertNotNull(entity);

		assertNotNull(entity.getId());
		assertNotNull(entity.getUsername());

		assertTrue(!entity.getId().toString().isBlank());

		assertEquals(username, entity.getUsername());
	}
}
