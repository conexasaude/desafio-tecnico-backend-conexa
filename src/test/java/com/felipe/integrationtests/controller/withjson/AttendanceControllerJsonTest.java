package com.felipe.integrationtests.controller.withjson;

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
import com.felipe.configs.TestConfigs;
import com.felipe.integrationtests.model.dto.AccountCredentialsDTO;
import com.felipe.integrationtests.model.dto.AttendanceDTO;
import com.felipe.integrationtests.model.dto.CreateUserDoctorDTO;
import com.felipe.integrationtests.model.dto.DoctorDTO;
import com.felipe.integrationtests.model.dto.PatientDTO;
import com.felipe.integrationtests.model.dto.wrapper.WrapperAttendanceDTO;
import com.felipe.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AttendanceControllerJsonTest extends AbstractIntegrationTest {
	private Logger logger = Logger.getLogger(AttendanceControllerJsonTest.class.getName());

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static AttendanceDTO dto;
	private static PatientDTO patientDTO;

	private static CreateUserDoctorDTO createDto;

	private static String accessToken;
	private static String refreshToken;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		dto = new AttendanceDTO();
		patientDTO = new PatientDTO();
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
	public void testCreateAttendance() throws JsonMappingException, JsonProcessingException {
		mockPatient();
		mockAttendance();
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/v1/attendance").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT).body(dto).when().post();

		logger.info("Status code: " + content.statusCode());
		logger.info("Response body: " + content.getBody().asString());

		var contentString = content.then().statusCode(201).extract().body().asString();

		logger.info("Persisted:  => " + content.toString());
		AttendanceDTO persisted = objectMapper.readValue(contentString, AttendanceDTO.class);
		dto = persisted;

		assertNotNull(persisted);
		logger.info("Persisted:  => " + persisted.toString());

		assertNotNull(persisted.getId());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("2024-08-03 09:00:00", persisted.getDateTime());
		assertEquals("535.921.270-55", persisted.getPatient().getCpf());
		assertEquals("Julaina Siqueira", persisted.getPatient().getFullName());
	}

	@Test
	@Order(3)
	public void testCreateAttendancePassedDate() throws JsonMappingException, JsonProcessingException {
		mockPatient();
		mockAttendance();

		dto.setDateTime("2014-08-03 09:00:00");

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT).body(dto).when().post();

		logger.info("Status code: " + content.statusCode());
		logger.info("Response body: " + content.getBody().asString());

		var contentString = content.then().statusCode(400).extract().body().asString();

		logger.info("Persisted:  => " + content.toString());
		String message = objectMapper.readTree(contentString).get("message").asText();
		String details = objectMapper.readTree(contentString).get("details").asText();

		logger.info("Persisted:  => " + contentString);

		assertEquals("Invalid request content.", message);
		assertEquals("dateTime: The date and time must be in the future", details);
	}

	@Test
	@Order(4)
	public void testFindAllAttendance() throws JsonMappingException, JsonProcessingException {
		logger.info("testFindAllAttendance => " + "   /api/v1/attendance");

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT).when().get().then().statusCode(200)
				.extract().body().asString();

		logger.info("testFindAllAttendance => " + content.toString());
		
		WrapperAttendanceDTO wrapper = objectMapper.readValue(content, WrapperAttendanceDTO.class);
	    logger.info("wrapper => " + wrapper.toString());
	    List<AttendanceDTO> persisted = wrapper.getEmbeddedDTO().getDtos();

		assertNotNull(persisted);
		assertTrue(persisted.size() > 0);
		assertNotNull(persisted.get(0).getId());
		assertTrue(!persisted.get(0).getId().toString().isBlank());
	}

	@Test
	@Order(5)
	public void testFindByIdAttendance() throws JsonMappingException, JsonProcessingException {
		mockPatient();
		mockAttendance();

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT).pathParam("id", dto.getId()).when()
				.get("{id}").then().statusCode(200).extract().body().asString();

		AttendanceDTO persisted = objectMapper.readValue(content, AttendanceDTO.class);
		dto = persisted;

		assertNotNull(persisted);

		assertNotNull(persisted.getId());
		assertNotNull(persisted.getDateTime());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("2024-08-03 09:00:00", persisted.getDateTime());
	}

	@Test
	@Order(6)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FELIPE).pathParam("id", dto.getId()).when()
				.get("{id}").then().statusCode(403).extract().body().asString();

		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	@Test
	@Order(7)
	public void testUpdateAttendance() throws JsonMappingException, JsonProcessingException {
		mockPatient();

		String newDateTime = "2024-12-03 09:00:00";

		dto.setDateTime(newDateTime);
		dto.setPatient(patientDTO);

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT).body(dto).when().put().then()
				.statusCode(200).extract().body().asString();

		AttendanceDTO persisted = objectMapper.readValue(content, AttendanceDTO.class);
		dto = persisted;

		assertNotNull(persisted);

		assertNotNull(persisted.getId());
		assertNotNull(persisted.getDateTime());
		assertNotNull(persisted.getPatient().getFullName());
		assertNotNull(persisted.getPatient().getCpf());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals(newDateTime, persisted.getDateTime());
	}

	@Test
	@Order(7)
	public void testDeleteByIdAttendance() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT).pathParam("id", dto.getId()).when()
				.delete("{id}").then().statusCode(204).extract().body().asString();
		logger.info("testDeleteByIdAttendance => " + content);
	}

	@Test
	@Order(7)
	public void testFindByIdAttendanceWithNotFound() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT).pathParam("id", dto.getId()).when()
				.get("{id}").then().statusCode(404).extract().body().asString();
		logger.info("testFindByIdAttendanceWithNotFound => " + content);
		String message = objectMapper.readTree(content).get("message").asText();
		assertNotNull(content);
		assertEquals("No records found", message);
	}

	private void mockCreateDoctor() {
		createDto.setFullName("Castro Alves");
		createDto.setEmail("castro.alves@gmail.com");
		createDto.setCpf("728.431.380-35");
		createDto.setPhone("(21) 83232-6565");
		createDto.setSpecialty("Cardiologista");
		createDto.setBirthDate("10/03/1980");
		createDto.setPassword("senhaNova");
		createDto.setConfirmPassword("senhaNova");
	}

	private void mockAttendance() {
		dto.setDateTime("2024-08-03 09:00:00");
		dto.setPatient(patientDTO);
	}

	private void mockPatient() {
		patientDTO.setCpf("535.921.270-55");
		patientDTO.setFullName("Julaina Siqueira");
		patientDTO.setHealthInsurance("SulAmerica");
	}

}
