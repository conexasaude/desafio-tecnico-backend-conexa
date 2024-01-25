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
import com.felipe.integrationtests.model.dto.CreateUserDoctorDTO;
import com.felipe.integrationtests.model.dto.DoctorDTO;
import com.felipe.integrationtests.model.dto.wrapper.WrapperDoctorDTO;
import com.felipe.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class DoctorControllerJsonTest extends AbstractIntegrationTest {
	private Logger logger = Logger.getLogger(DoctorControllerJsonTest.class.getName());

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static DoctorDTO dto;

	private static CreateUserDoctorDTO createDto;

	private static String accessToken;
	private static String refreshToken;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		dto = new DoctorDTO();
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
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).body(createDto).when().post();


		logger.info("Status code: " + content.statusCode());
		logger.info("Response body: " + content.getBody().asString());

		if (content.getBody().asString().contains("Records already exist in the database") ) {
			logger.info("User already Signup");
		} else {
			var contentString = content.then().statusCode(201).extract().body().asString();
			
			DoctorDTO persisted = objectMapper.readValue(contentString, DoctorDTO.class);
			dto = persisted;
			
			assertNotNull(persisted);
			logger.info("Persisted:  => " + persisted.toString());
			
			assertNotNull(persisted.getId());
			
			assertTrue(!persisted.getId().toString().isBlank());			
		}
		
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
	public void testFindAllDoctor() throws JsonMappingException, JsonProcessingException {
		logger.info("testFindAll => " + "   /api/v1/doctor");

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/v1/doctor")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		logger.info("testFindAll => " + content.toString());

		WrapperDoctorDTO wrapper = objectMapper.readValue(content, WrapperDoctorDTO.class);

	    logger.info("wrapper => " + wrapper.toString());
	    List<DoctorDTO> persisted = wrapper.getEmbeddedDTO().getDtos();
	    String email = createDto.getEmail();
	    logger.info("email => " + email);

	    dto = persisted.stream()
	    		.filter(e -> e.getEmail().equalsIgnoreCase(createDto.getEmail()))
	    		.findFirst()
	    		.orElse(null);
	    logger.info("found => " + dto.toString());
	    
		assertNotNull(persisted);
		assertTrue(persisted.size() > 0);
		assertNotNull(persisted.get(0).getId());
		assertTrue(!persisted.get(0).getId().toString().isBlank());

	}

	@Test
	@Order(3)
	public void testUpdateDoctor() throws JsonMappingException, JsonProcessingException {
		logger.info(dto.toString());
		mockDoctor();
		dto.setFullName("Amanda Torres Da Silva ");
		dto.setSpecialty("Dermatologista");

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
					.body(dto)
					.when()
					.put()
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

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("Amanda Torres Da Silva ", persisted.getFullName());
		assertEquals("Dermatologista", persisted.getSpecialty());

		assertEquals("amanda.torres@gmail.com", persisted.getEmail());
		assertEquals("377.466.280-01", persisted.getCpf());
		assertEquals("(21) 83232-6565", persisted.getPhone());
		assertEquals("10/03/1980", persisted.getBirthDate());
	}


	@Test
	@Order(4)
	public void testFindByIdDoctor() throws JsonMappingException, JsonProcessingException {
		mockDoctor();


		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
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
		logger.info("testFindByIdDoctor => " + content);
		logger.info("testFindByIdDoctor => " + persisted);

		assertNotNull(persisted);

		assertNotNull(persisted.getId());
		assertNotNull(persisted.getFullName());
		assertNotNull(persisted.getEmail());
		assertNotNull(persisted.getCpf());
		assertNotNull(persisted.getPhone());
		assertNotNull(persisted.getSpecialty());
		assertNotNull(persisted.getBirthDate());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("Amanda Torres Da Silva ", persisted.getFullName());
		assertEquals("Dermatologista", persisted.getSpecialty());
		assertEquals("amanda.torres@gmail.com", persisted.getEmail());
		assertEquals("377.466.280-01", persisted.getCpf());
		assertEquals("(21) 83232-6565", persisted.getPhone());
		assertEquals("10/03/1980", persisted.getBirthDate());
	}

	@Test
	@Order(5)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
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
	public void testFindByFullNameDoctor() throws JsonMappingException, JsonProcessingException {
		mockDoctor();


		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
					.pathParam("fullName", dto.getFullName())
					.when()
					.get("/findAllByFullName/{fullName}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		logger.info("testFindByFullNameDoctor => " + content);
		WrapperDoctorDTO wrapper = objectMapper.readValue(content, WrapperDoctorDTO.class);
	    logger.info("wrapper => " + wrapper.toString());
	    List<DoctorDTO> persistedList = wrapper.getEmbeddedDTO().getDtos();	

	    DoctorDTO persisted = persistedList.getFirst();
		logger.info("testFindByFullNameDoctor => " + persisted);

		assertNotNull(persisted);

		assertNotNull(persisted.getId());
		assertNotNull(persisted.getFullName());
		assertNotNull(persisted.getEmail());
		assertNotNull(persisted.getCpf());
		assertNotNull(persisted.getPhone());
		assertNotNull(persisted.getSpecialty());
		assertNotNull(persisted.getBirthDate());

		assertTrue(!persisted.getId().toString().isBlank());

		assertEquals("Amanda Torres Da Silva ", persisted.getFullName());
		assertEquals("Dermatologista", persisted.getSpecialty());
		assertEquals("amanda.torres@gmail.com", persisted.getEmail());
		assertEquals("377.466.280-01", persisted.getCpf());
		assertEquals("(21) 83232-6565", persisted.getPhone());
		assertEquals("10/03/1980", persisted.getBirthDate());
	}

	@Test
	@Order(7)
	public void testDeleteByEmailDoctor() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
					.pathParam("email", dto.getEmail())
					.when()
					.delete("{email}")
				.then()
					.statusCode(204)
				.extract()
					.body()
						.asString();
		logger.info("testDeleteByemailDoctor => " + content);
	}

	@Test
	@Order(8)
	public void testFindByIdDoctorWithNotFound() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
					.pathParam("id", dto.getId())
					.when()
					.get("{id}");
		
		int statusCode = content.statusCode();
		String body = content.getBody().asString();
		logger.info("Status code: " + statusCode);
		logger.info("Response body: " + body);

		if (statusCode == 403) {
			assertTrue(body.contains("No records found: Email " + dto.getEmail() + " not found!"));
			logger.info("User completed deleted");
		} else {
			logger.info("testFindByIdDoctorWithNotFound => " + content);
			String message = objectMapper.readTree(body).get("message").asText();
			assertNotNull(content);
			assertEquals("No records found", message);
		}
		
	}


	private void mockDoctor() {
		dto.setFullName("Amanda Torres");
		dto.setEmail("amanda.torres@gmail.com");
		dto.setCpf("377.466.280-01");
		dto.setPhone("(21) 83232-6565");
		dto.setSpecialty("Cardiologista");
		dto.setBirthDate("10/03/1980");
		dto.setPassword("senhaNova");
	}

	private void mockCreateDoctor() {
		createDto.setFullName("Amanda Torres");
		createDto.setEmail("amanda.torres@gmail.com");
		createDto.setCpf("377.466.280-01");
		createDto.setPhone("(21) 83232-6565");
		createDto.setSpecialty("Cardiologista");
		createDto.setBirthDate("10/03/1980");
		createDto.setPassword("senhaNova");
		createDto.setConfirmPassword("senhaNova");
	}

}
