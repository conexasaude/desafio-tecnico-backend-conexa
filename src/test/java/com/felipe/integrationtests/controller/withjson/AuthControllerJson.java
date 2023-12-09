package com.felipe.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.felipe.configs.TestConfigs;
import com.felipe.integrationtests.model.dto.AccountCredentialsDTO;
import com.felipe.integrationtests.model.dto.CreateUserDoctorDTO;
import com.felipe.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerJson extends AbstractIntegrationTest {
	private Logger logger = Logger.getLogger(AuthControllerJson.class.getName());

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static CreateUserDoctorDTO dto;

	private static String accessToken;
	private static String refreshToken;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		dto = new CreateUserDoctorDTO();
	}

	@Test
	@Order(1)
	public void testSignup() throws JsonMappingException, JsonProcessingException {
		mockDoctor();
		specification = new RequestSpecBuilder().addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.setBasePath("/api/v1/signup").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).body(dto).when().post()
				.then().statusCode(201).extract().body().asString();

		logger.info("Persisted:  => " + content.toString());
		CreateUserDoctorDTO persisted = objectMapper.readValue(content, CreateUserDoctorDTO.class);
		dto = persisted;

		assertNotNull(persisted);
		logger.info("Persisted:  => " + persisted.toString());

		assertNotNull(persisted.getId());
		assertNotNull(persisted.getFullName());
		assertNotNull(persisted.getEmail());
		assertNotNull(persisted.getCpf());
		assertNotNull(persisted.getPhone());
		assertNotNull(persisted.getSpecialty());
		assertNotNull(persisted.getBirthDate());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("Marcia Oliveira", persisted.getFullName());
		assertEquals("marcia_oliveira@gmail.com", persisted.getEmail());
		assertEquals("483.127.330-94", persisted.getCpf());
		assertEquals("(21) 83232-6565", persisted.getPhone());
		assertEquals("Cardiologista", persisted.getSpecialty());
		assertEquals("10/03/1980", persisted.getBirthDate());
	}

	@Test
	@Order(2)
	public void testSignupWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockDoctor();

		specification = new RequestSpecBuilder().addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FELIPE)
				.setBasePath("/api/v1/doctor").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).body(dto).when().post()
				.then().statusCode(403).extract().body().asString();

		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	@Test
	@Order(3)
	public void testLogin() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsDTO userLogin = new AccountCredentialsDTO(dto.getEmail(), dto.getPassword());
		logger.info("userLogin:  => " + userLogin.toString());

		var content = given().basePath("/api/v1/login").port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON).body(userLogin).when().post();
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
	@Order(4)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {
		logger.info("Refresh => " + "   /api/v1/refresh/" + dto.getEmail());
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + refreshToken)
				.setBasePath("/api/v1/refresh/" + dto.getEmail()).setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.body("{\"token\":\"" + accessToken + "\"}").when().put();
		int statusCode = content.statusCode();
		String responseBody = content.getBody().asString();
		refreshToken = content.getHeader("Refresh-Token");

		logger.info("Status code: " + statusCode);
		logger.info("Response body: " + responseBody);
		logger.info("refreshToken: " + refreshToken);

		content.then().statusCode(200);

		accessToken = content.jsonPath().getString("token");
		logger.info("REFRESH_ROUTE =>  token: " + accessToken);

		assertNotNull(accessToken);
		assertNotNull(refreshToken);

	}

	@Test
	@Order(5)
	public void testLogout() throws JsonMappingException, JsonProcessingException {

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/v1/logout").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.body("").when().post();

		int statusCode = content.statusCode();
		String responseBody = content.getBody().asString();

		logger.info("Response: " + responseBody);
		logger.info("statusCode: " + statusCode);


		assertNotNull(responseBody);
//		assertEquals("Token has been revoked!", responseBody);
		assertEquals("Logged out successfully", responseBody);
		assertEquals(200, statusCode);

	}

	private void mockDoctor() {
		dto.setFullName("Marcia Oliveira");
		dto.setEmail("marcia_oliveira@gmail.com");
		dto.setCpf("483.127.330-94");
		dto.setPhone("(21) 83232-6565");
		dto.setSpecialty("Cardiologista");
		dto.setBirthDate("10/03/1980");
		dto.setPassword("senhaNova");
		dto.setConfirmPassword("senhaNova");
	}

}
