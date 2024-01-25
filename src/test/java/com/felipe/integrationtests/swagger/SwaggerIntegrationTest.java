package com.felipe.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.felipe.configs.TestConfigs;
import com.felipe.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void shouldDisplaySwaggerUriPage() {
		var content = given()
				.basePath("/swagger-ui/index.html")
				.port(TestConfigs.SERVER_PORT)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		assertTrue(content.contains("Swagger UI"));
	}

}
