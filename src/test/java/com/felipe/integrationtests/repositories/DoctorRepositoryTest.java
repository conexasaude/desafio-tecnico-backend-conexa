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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.felipe.integrationtests.testcontainers.AbstractIntegrationTest;
import com.felipe.model.Doctor;
import com.felipe.repositories.DoctorRepository;
import com.felipe.unittests.mapper.mocks.MockDoctor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class DoctorRepositoryTest extends AbstractIntegrationTest {
	private Logger logger = Logger.getLogger(DoctorRepositoryTest.class.getName());

	@Autowired
	public DoctorRepository repository;

	private static Doctor entity;
	private static MockDoctor inputObject = new MockDoctor();

	@BeforeAll
	public static void setup() {
		entity = new Doctor();
		entity= inputObject.mockRandomEntity(false);

	}

	@Test
	@Order(0)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		logger.info("testCreate=> ");

		Doctor createdEntity = repository.save(entity);
		entity = createdEntity;
		logger.info(createdEntity.toString());
	}

	@Test
	@Order(1)
	public void testFindByPartialName() throws JsonMappingException, JsonProcessingException {
		logger.info("findByPartialName => " + "   /findAllByFullName/{fullName}");
		
		Pageable pageable = PageRequest.of(0, 6, Sort.by(Direction.ASC, "fullName"));
		
		String fullName = entity.getFullName();

		Doctor entityFound = repository.findByPartialName(fullName, pageable).getContent().get(0);
		logger.info("testFindByEmail => " + entityFound.toString());

		assertNotNull(entityFound);

		assertNotNull(entityFound.getId());
		assertNotNull(entityFound.getFullName());

		assertTrue(!entityFound.getId().toString().isBlank());

		assertEquals(fullName, entityFound.getFullName());
	}
}
