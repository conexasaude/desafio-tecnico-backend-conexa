package com.felipe.integrationtests.controller.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.logging.Logger;

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
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.felipe.configs.TestConfigs;
import com.felipe.integrationtests.model.dto.AccountCredentialsDTO;
import com.felipe.integrationtests.model.dto.CreateUserDoctorDTO;
import com.felipe.integrationtests.model.dto.DoctorDTO;
import com.felipe.integrationtests.model.dto.PatientDTO;
import com.felipe.integrationtests.model.dto.wrapper.WrapperPatientDTO;
import com.felipe.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PatientControllerXmlTest extends AbstractIntegrationTest {
	private Logger logger = Logger.getLogger(PatientControllerXmlTest.class.getName());

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static ObjectMapper xmlMapper;

	private static PatientDTO dto;

	private static CreateUserDoctorDTO createDto;

	private static String accessToken;
	private static String refreshToken;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		xmlMapper = new XmlMapper();
		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		dto = new PatientDTO();
		createDto = new CreateUserDoctorDTO();
	}

	@Test
	@Order(0)
	public void testSignup() throws JsonMappingException, JsonProcessingException {
		mockCreateDoctor();
		String xmlDto = xmlMapper.writeValueAsString(createDto);


		specification = new RequestSpecBuilder().addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.setBasePath("/api/v1/signup").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_XML).body(xmlDto).when()
				.post();

		logger.info("Status code: " + content.statusCode());
		logger.info("Response body: " + content.getBody().asString());

		var contentString = content.then().statusCode(201).extract().body().asString();

		logger.info("Persisted:  => " + content.toString());
		DoctorDTO persisted = objectMapper.readValue(contentString, DoctorDTO.class);

		assertNotNull(persisted);
		logger.info("Persisted:  => " + persisted.toString());

		assertNotNull(persisted.getId());

		assertTrue(!persisted.getId().toString().isBlank());
	}

	@Test
	@Order(1)
	public void testLogin() throws JsonMappingException, JsonProcessingException {
		String xmlDto = xmlMapper.writeValueAsString(new AccountCredentialsDTO(createDto.getEmail(), createDto.getPassword()));

		logger.info("userLogin:  => " + xmlDto.toString());

		var content = given().basePath("/api/v1/login").port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML).body(xmlDto).when().post();
		int statusCode = content.statusCode();
		String responseBody = content.getBody().asString();
		refreshToken = content.getHeader("Refresh-Token");

		logger.info("Status code: " + statusCode);
		logger.info("Response body: " + responseBody);
		logger.info("refreshToken: " + refreshToken);

		content.then().statusCode(200);

		accessToken = content.jsonPath().getString("token");

		assertNotNull(accessToken);
		assertNotNull(refreshToken);
	}


	@Test
	@Order(2)
	public void testCreatePatient() throws JsonMappingException, JsonProcessingException {
		mockPatient();

		String xmlDto = xmlMapper.writeValueAsString(dto);

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/v1/patient").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.body(xmlDto).when()
				.post();

		logger.info("Status code: " + content.statusCode());
		logger.info("Response body: " + content.getBody().asString());

		var contentString = content.then().statusCode(201).extract().body().asString();

		logger.info("Persisted:  => " + content.toString());
		PatientDTO persisted = objectMapper.readValue(contentString, PatientDTO.class);
		dto = persisted;

		assertNotNull(persisted);
		logger.info("Persisted:  => " + persisted.toString());

		assertNotNull(persisted.getId());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("Lucas Silva Mello", persisted.getFullName());
		assertEquals("909.662.660-56", persisted.getCpf());
		assertEquals("SulAmerica", persisted.getHealthInsurance());
	}

	@Test
	@Order(3)
	public void testFindAllPatient() throws JsonMappingException, JsonProcessingException {
		logger.info("testFindAll => " + "   /api/v1/patient");

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/v1/patient")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		logger.info("testFindAll => " + content.toString());

		WrapperPatientDTO wrapper = objectMapper.readValue(content, WrapperPatientDTO.class);
	    logger.info("wrapper => " + wrapper.toString());
	    List<PatientDTO> persisted = wrapper.getEmbeddedDTO().getDtos();
		
		assertNotNull(persisted);
		assertTrue(persisted.size() > 0);
		assertNotNull(persisted.get(0).getId());
		assertTrue(!persisted.get(0).getId().toString().isBlank());
	}

	@Test
	@Order(4)
	public void testFindByIdPatient() throws JsonMappingException, JsonProcessingException {
		mockPatient();


		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
					.pathParam("id", dto.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		PatientDTO persisted = objectMapper.readValue(content, PatientDTO.class);
		dto = persisted;

		assertNotNull(persisted);

		assertNotNull(persisted.getId());
		assertNotNull(persisted.getFullName());
		assertNotNull(persisted.getCpf());
		assertNotNull(persisted.getHealthInsurance());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("Lucas Silva Mello", persisted.getFullName());
		assertEquals("909.662.660-56", persisted.getCpf());
		assertEquals("SulAmerica", persisted.getHealthInsurance());
	}

	@Test
	@Order(5)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FELIPE)
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

	@Test
	@Order(6)
	public void testUpdateDoctor() throws JsonMappingException, JsonProcessingException {
		mockPatient();

		String newName = "Lucas Silva Mello dos Anjos";

		dto.setFullName(newName);
		String xmlDto = xmlMapper.writeValueAsString(dto);

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
					.body(xmlDto)
					.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		PatientDTO persisted = objectMapper.readValue(content, PatientDTO.class);
		dto = persisted;

		assertNotNull(persisted);

		assertNotNull(persisted.getId());
		assertNotNull(persisted.getFullName());
		assertNotNull(persisted.getCpf());
		assertNotNull(persisted.getHealthInsurance());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals(newName, persisted.getFullName());
		assertEquals("909.662.660-56", persisted.getCpf());
		assertEquals("SulAmerica", persisted.getHealthInsurance());
	}

	@Test
	@Order(7)
	public void testDeleteByIdDoctor() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
					.pathParam("id", dto.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204)
				.extract()
					.body()
						.asString();
		logger.info("testDeleteByIdDoctor => " + content);
	}

	@Test
	@Order(7)
	public void testFindByIdDoctorWithNotFound() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
					.pathParam("id", dto.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(404)
				.extract()
					.body()
						.asString();
		logger.info("testFindByIdDoctorWithNotFound => " + content);
		String message = objectMapper.readTree(content).get("message").asText();
		assertNotNull(content);
		assertEquals("No records found", message);
	}


	private void mockCreateDoctor() {
		createDto.setFullName("Marcondes Carvalho");
		createDto.setEmail("marcondes.carvalho.s@gmail.com");
		createDto.setCpf("538.127.550-17");
		createDto.setPhone("(21) 83232-6565");
		createDto.setSpecialty("Cardiologista");
		createDto.setBirthDate("10/03/1980");
		createDto.setPassword("senhaNova");
		createDto.setConfirmPassword("senhaNova");
	}

	private void mockPatient() {
		dto.setCpf("909.662.660-56");
		dto.setFullName("Lucas Silva Mello");
		dto.setHealthInsurance("SulAmerica");
	}
}
