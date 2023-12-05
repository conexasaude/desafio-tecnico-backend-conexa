package com.felipe.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipe.configs.TestConfigs;
import com.felipe.integrationtests.model.dto.DoctorDTO;
import com.felipe.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class DoctorControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static DoctorDTO dto;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		dto = new DoctorDTO();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockDoctor();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.setBasePath("/api/v1/doctor")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.body(dto)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		DoctorDTO persisted = objectMapper.readValue(content, DoctorDTO.class);
		dto = persisted;
		
		assertNotNull(persisted);
		
		assertNotNull(persisted.getId());
		assertNotNull(persisted.getFullName());
		assertNotNull(persisted.getEmail());
		assertNotNull(persisted.getCpf());
		assertNotNull(persisted.getPhone());
		assertNotNull(persisted.getSpecialty());
		assertNotNull(persisted.getBirthDate());
//		assertNotNull(created.getPassword()); // Não é serializado
		
		assertTrue(!persisted.getId().toString().isBlank());
		
		assertEquals("Richard Stallman", persisted.getFullName());
		assertEquals("richard.stallman@mail.com", persisted.getEmail());
		assertEquals("810.231.566-44", persisted.getCpf());
		assertEquals("(81) 5412-2521", persisted.getPhone());
		assertEquals("Cardiologista", persisted.getSpecialty());
		assertEquals("16/04/1953", persisted.getBirthDate());
//		assertEquals("OpenSourcePai123", created.getPassword()); // // Não é serializado
	}
	
	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockDoctor();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FELIPE)
				.setBasePath("/api/v1/doctor")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.body(dto)
					.when()
					.post()
				.then()
					.statusCode(403)
				.extract()
					.body()
						.asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockDoctor();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.setBasePath("/api/v1/doctor")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", dto.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		DoctorDTO persisted = objectMapper.readValue(content, DoctorDTO.class);
		dto = persisted;
		
		assertNotNull(persisted);
		
		assertNotNull(persisted.getId());
		assertNotNull(persisted.getFullName());
		assertNotNull(persisted.getEmail());
		assertNotNull(persisted.getCpf());
		assertNotNull(persisted.getPhone());
		assertNotNull(persisted.getSpecialty());
		assertNotNull(persisted.getBirthDate());
//		assertNotNull(created.getPassword()); // Não é serializado
		
		assertTrue(!persisted.getId().toString().isBlank());
		
		assertEquals("Richard Stallman", persisted.getFullName());
		assertEquals("richard.stallman@mail.com", persisted.getEmail());
		assertEquals("810.231.566-44", persisted.getCpf());
		assertEquals("(81) 5412-2521", persisted.getPhone());
		assertEquals("Cardiologista", persisted.getSpecialty());
		assertEquals("16/04/1953", persisted.getBirthDate());
//		assertEquals("OpenSourcePai123", created.getPassword()); // // Não é serializado
	}
	
	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockDoctor();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FELIPE)
				.setBasePath("/api/v1/doctor")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", dto.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(403)
				.extract()
					.body()
						.asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);

	}
	
	

	private void mockDoctor() {
		dto.setFullName("Richard Stallman");
		dto.setEmail("richard.stallman@mail.com");
		dto.setCpf("810.231.566-44");
		dto.setPhone("(81) 5412-2521");
		dto.setSpecialty("Cardiologista");
		dto.setBirthDate("16/04/1953");
		dto.setPassword("OpenSourcePai123");
	}

}
