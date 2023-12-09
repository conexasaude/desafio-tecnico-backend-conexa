package com.felipe.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import com.felipe.integrationtests.model.dto.DoctorDTO;
import com.felipe.integrationtests.model.dto.PasswordUpdateDTO;
import com.felipe.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerJsonTest extends AbstractIntegrationTest {
	private Logger logger = Logger.getLogger(UserControllerJsonTest.class.getName());

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static DoctorDTO doctorDTO;
	private static PasswordUpdateDTO passwordUpdateDTO;

	private static CreateUserDoctorDTO createDto;

	private static String accessToken;
	private static String refreshToken;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		doctorDTO = new DoctorDTO();
		passwordUpdateDTO = new PasswordUpdateDTO();
		createDto = new CreateUserDoctorDTO();
	}

	@Test
	@Order(0)
	public void testSignup() throws JsonMappingException, JsonProcessingException {
		mockCreateDoctor();
		specification = new RequestSpecBuilder().addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.setBasePath("/api/v1/signup").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).body(createDto).when()
				.post();

		logger.info("Status code: " + content.statusCode());
		logger.info("Response body: " + content.getBody().asString());

		var contentString = content.then().statusCode(201).extract().body().asString();

		logger.info("Persisted:  => " + content.toString());
		DoctorDTO persisted = objectMapper.readValue(contentString, DoctorDTO.class);
		doctorDTO = persisted;

		assertNotNull(persisted);
		logger.info("Persisted:  => " + persisted.toString());

		assertNotNull(persisted.getId());

		assertTrue(!persisted.getId().toString().isBlank());
	}

	@Test
	@Order(1)
	public void testLogin() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsDTO userLogin = new AccountCredentialsDTO(createDto.getEmail(), createDto.getPassword());
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
	@Order(2)
	public void testUpdatePasswordChange() throws JsonMappingException, JsonProcessingException {
		logger.info("testUpdatePasswordChange => " + "   /api/v1/user/{email}/password");
		mockChangePassword();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/v1/user/{email}/password").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
				.pathParam("email", createDto.getEmail())
				.body(passwordUpdateDTO).when().patch()
					.then()
					.statusCode(204)
					.extract()
					.body()
					.asString();
	}
	
	@Test
	@Order(3)
	public void testLoginFalidPassword() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsDTO userLogin = new AccountCredentialsDTO(createDto.getEmail(), createDto.getPassword());
		logger.info("userLogin:  => " + userLogin.toString());

		var content = given().basePath("/api/v1/login").port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON).body(userLogin).when().post();
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
		AccountCredentialsDTO userLogin = new AccountCredentialsDTO(createDto.getEmail(), passwordUpdateDTO.getNewPassword());
		logger.info("userLogin:  => " + userLogin.toString());

		var content = given().basePath("/api/v1/login").port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON).body(userLogin).when().post();
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

	private void mockCreateDoctor() {
		createDto.setFullName("João Paulo Souza");
		createDto.setEmail("jp.souza@gmail.com");
		createDto.setCpf("696.456.830-17");
		createDto.setPhone("(21) 83232-6565");
		createDto.setSpecialty("Cardiologista");
		createDto.setBirthDate("10/03/1980");
		createDto.setPassword("senhaNova");
		createDto.setConfirmPassword("senhaNova");
	}

	private void mockChangePassword() {
		passwordUpdateDTO.setOldPassword("senhaNova");
		passwordUpdateDTO.setNewPassword("senhaNova123");
		passwordUpdateDTO.setConfirmNewPassword("senhaNova123");
	}
}
