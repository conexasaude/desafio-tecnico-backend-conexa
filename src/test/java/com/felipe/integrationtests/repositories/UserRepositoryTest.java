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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

	private static User entity;
	private static MockUser inputObject = new MockUser();

	@BeforeAll
	public static void setup() {
		entity = new User();
		entity = inputObject.mockRandomEntity(false);

	}

	@Test
	@Order(0)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		logger.info("testCreate=> ");

		User createdEntity = repository.save(entity);
		entity = createdEntity;
		logger.info(createdEntity.toString());
	}

	@Test
	@Order(1)
	public void testFindByEmail() throws JsonMappingException, JsonProcessingException {
		logger.info("testFindByEmail => " + "   /api/v1/user/email/{email}");
		String username = entity.getUsername();

		User entity = repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(
				MessageUtils.NO_RECORDS_FOUND + ": Email " + username + " not found!"));
		logger.info("testFindByEmail => " + entity.toString());

		assertNotNull(entity);

		assertNotNull(entity.getId());
		assertNotNull(entity.getUsername());

		assertTrue(!entity.getId().toString().isBlank());

		assertEquals(username, entity.getUsername());
	}
}
