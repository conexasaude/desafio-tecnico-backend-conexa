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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.felipe.configs.TestConfigs;
import com.felipe.integrationtests.model.dto.AccountCredentialsDTO;
import com.felipe.integrationtests.model.dto.CreateUserDoctorDTO;
import com.felipe.integrationtests.model.dto.DoctorDTO;
import com.felipe.integrationtests.model.dto.PasswordUpdateDTO;
import com.felipe.integrationtests.model.dto.UserDTO;
import com.felipe.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerXmlTest extends AbstractIntegrationTest {
	private Logger logger = Logger.getLogger(UserControllerXmlTest.class.getName());

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static ObjectMapper xmlMapper;

	private static PasswordUpdateDTO passwordUpdateDTO;

	private static CreateUserDoctorDTO createDto;
	private static UserDTO dto;

	private static String accessToken;
	private static String refreshToken;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		xmlMapper = new XmlMapper();
		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		passwordUpdateDTO = new PasswordUpdateDTO();
		createDto = new CreateUserDoctorDTO();
		dto = new UserDTO();
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
	public void testUpdatePasswordChange() throws JsonMappingException, JsonProcessingException {
		logger.info("testUpdatePasswordChange => " + "   /api/v1/user/{email}/password");
		mockChangePassword();
		String xmlDto = xmlMapper.writeValueAsString(passwordUpdateDTO);

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/v1/user/{email}/password").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.pathParam("email", createDto.getEmail())
				.body(xmlDto).when().patch()
					.then()
					.statusCode(204)
					.extract()
					.body()
					.asString();
	}

	@Test
	@Order(3)
	public void testLoginFalidPassword() throws JsonMappingException, JsonProcessingException {
		String xmlDto = xmlMapper.writeValueAsString(new AccountCredentialsDTO(createDto.getEmail(), createDto.getPassword()));
		logger.info("userLogin:  => " + xmlDto.toString());

		var content = given().basePath("/api/v1/login").port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML).body(xmlDto).when().post();
		int statusCode = content.statusCode();
		String responseBody = content.getBody().asString();
		refreshToken = content.getHeader("Refresh-Token");

		logger.info("Status code: " + statusCode);
		logger.info("Response body: " + responseBody);

		content.then().statusCode(401);

	}

	@Test
	@Order(3)
	public void testLoginWithNewPassword() throws JsonMappingException, JsonProcessingException {
		String xmlDto = xmlMapper.writeValueAsString(new AccountCredentialsDTO(createDto.getEmail(), passwordUpdateDTO.getNewPassword()));
		logger.info("userLogin:  => " + xmlDto.toString());

		var content = given().basePath("/api/v1/login").port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML).body(xmlDto).when().post();
		int statusCode = content.statusCode();
		String responseBody = content.getBody().asString();
		refreshToken = content.getHeader("Refresh-Token");

		logger.info("Status code: " + statusCode);
		logger.info("Response body: " + responseBody);

		content.then().statusCode(200);

		accessToken = content.jsonPath().getString("token");

		assertNotNull(accessToken);
		assertNotNull(refreshToken);
	}

	@Test
	@Order(4)
	public void testFindAllUser() throws JsonMappingException, JsonProcessingException {
		logger.info("testFindAllUser => " + "   /api/v1/user");
		mockUser();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/v1/user").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
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


	    List<UserDTO> persisted = objectMapper.readValue(content, new TypeReference<List<UserDTO>>() {});
	    logger.info("testFindAll => " + persisted.toString());
	    logger.info("dto Before => " + dto.toString());

	    dto = persisted.stream().filter(userDTO -> dto.getEmail().equals(userDTO.getEmail())).findFirst().orElse(dto);
	    logger.info("dto => " + dto.toString());

		assertNotNull(persisted);
		assertTrue(persisted.size() > 0);
		assertNotNull(persisted.get(0).getId());
		assertTrue(!persisted.get(0).getId().toString().isBlank());
	}

	@Test
	@Order(5)
	public void testFindByIdUser() throws JsonMappingException, JsonProcessingException {

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

		UserDTO persisted = objectMapper.readValue(content, UserDTO.class);
	    logger.info("dto => " + persisted.toString());

		assertNotNull(persisted);

		assertNotNull(persisted.getId());
		assertNotNull(persisted.getEmail());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("jp.souza.santos@gmail.com", persisted.getEmail());
	}
	
	@Test
	@Order(6)
	public void testUpdateDisable() throws JsonMappingException, JsonProcessingException {
		logger.info("testUpdatePasswordChange => " + "   /api/v1/user/{id}/disable");


		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_XML)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.pathParam("id", dto.getId())
				.when().patch("{id}/disable")
					.then()
					.statusCode(200)
					.extract()
					.body()
					.asString();
		
		UserDTO persisted = objectMapper.readValue(content, UserDTO.class);
	    logger.info("dto => " + persisted.toString());

		assertNotNull(persisted);

		assertNotNull(persisted.getId());
		assertNotNull(persisted.getEmail());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("jp.souza.santos@gmail.com", persisted.getEmail());
		assertEquals(false, persisted.getEnabled());
	}
	
	@Test
	@Order(7)
	public void testFindByIdUserEnabled() throws JsonMappingException, JsonProcessingException {

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

		UserDTO persisted = objectMapper.readValue(content, UserDTO.class);
	    logger.info("dto => " + persisted.toString());

		assertNotNull(persisted);

		assertNotNull(persisted.getId());
		assertNotNull(persisted.getEmail());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("jp.souza.santos@gmail.com", persisted.getEmail());
		assertEquals(false, persisted.getEnabled());
	}
	
	private void mockCreateDoctor() {
		createDto.setFullName("João Paulo Souza");
		createDto.setEmail("jp.souza.santos@gmail.com");
		createDto.setCpf("666.264.830-21");
		createDto.setPhone("(21) 83232-6565");
		createDto.setSpecialty("Cardiologista");
		createDto.setBirthDate("10/03/1980");
		createDto.setPassword("senhaNova");
		createDto.setConfirmPassword("senhaNova");
	}
	
	private void mockUser() {
		dto.setEmail("jp.souza.santos@gmail.com");
		dto.setPassword("senhaNova");
		dto.setAccountNonExpired(true);
		dto.setAccountNonLocked(true);
		dto.setCredentialsNonExpired(true);
		dto.setEnabled(true);
		dto.setConfirmedEmail(true);
	}

	private void mockChangePassword() {
		passwordUpdateDTO.setOldPassword("senhaNova");
		passwordUpdateDTO.setNewPassword("senhaNova123");
		passwordUpdateDTO.setConfirmNewPassword("senhaNova123");
	}
}
