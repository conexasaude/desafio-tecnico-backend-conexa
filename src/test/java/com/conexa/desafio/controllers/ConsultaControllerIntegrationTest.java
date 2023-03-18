package com.conexa.desafio.controllers;

import com.conexa.desafio.helpers.TestDataHelper;
import com.conexa.desafio.payload.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

import static com.conexa.desafio.helpers.TestDataHelper.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConsultaControllerIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void deveAgendarConsultaComSucessoQuandoOTokenForValido()
      throws URISyntaxException {
    String token = cadastraEFazLogin();
    ConsultaRequest consultaRequest =
        TestDataHelper.deserializeObject(CONSULTA_VALIDA_REQUEST, ConsultaRequest.class);
    HttpEntity<String> entity = constroiHttpEntity(token, consultaRequest);
    ResponseEntity<String> consultaResponse =
        restTemplate.exchange(ATTENDANCE_ROUTE, HttpMethod.POST, entity, String.class);
    assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), consultaResponse.getStatusCode());
  }

  @Test
  void deveRetornarErroQuandoTentarAgendarUmaConsultaComTokenInvalido() {
    ConsultaRequest consultaRequest =
        TestDataHelper.deserializeObject(CONSULTA_VALIDA_REQUEST, ConsultaRequest.class);
    HttpEntity<String> entity = constroiHttpEntity(TOKEN_TEST_SEM_PREFIXO, consultaRequest);
    ResponseEntity<String> consultaResponse =
        restTemplate.exchange(ATTENDANCE_ROUTE, HttpMethod.POST, entity, String.class);
    assertEquals(
        HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()), consultaResponse.getStatusCode());
  }

  @Test
  void deveRetornarUnauthorizedQuandoTentarAcessarAposLogoff() throws URISyntaxException {
    String token = cadastraEFazLogin();
    HttpEntity<String> logoffEntity = constroiHttpEntity(token, "body");
    ResponseEntity<BaseResponse> logoffResponse =
            restTemplate.exchange(LOGOFF_ROUTE, HttpMethod.POST, logoffEntity, BaseResponse.class);
    assertEquals(
            HttpStatusCode.valueOf(HttpStatus.OK.value()), logoffResponse.getStatusCode());
    ConsultaRequest consultaRequest =
            TestDataHelper.deserializeObject(CONSULTA_VALIDA_REQUEST, ConsultaRequest.class);
    HttpEntity<String> consultaEntity = constroiHttpEntity(token, consultaRequest);
    ResponseEntity<BaseResponse> consultaResponse =
            restTemplate.exchange(ATTENDANCE_ROUTE, HttpMethod.POST, consultaEntity, BaseResponse.class);
    assertEquals(HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()), consultaResponse.getStatusCode());
  }

  private String cadastraEFazLogin() throws URISyntaxException {
    SignupRequest usuario =
            TestDataHelper.deserializeObject(SIGNUP_VALIDO_REQUEST, SignupRequest.class);
    ResponseEntity<BaseResponse> signupResponse =
            restTemplate.postForEntity(new URI(SIGNUP_ROUTE), usuario, BaseResponse.class);
    assertEquals(
            HttpStatusCode.valueOf(HttpStatus.CREATED.value()), signupResponse.getStatusCode());
    LoginRequest loginRequest =
            TestDataHelper.deserializeObject(LOGIN_VALIDO_REQUEST, LoginRequest.class);
    ResponseEntity<BaseResponse> loginResponse =
            restTemplate.postForEntity(new URI(LOGIN_ROUTE), loginRequest, BaseResponse.class);
    assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), loginResponse.getStatusCode());
    String token =
            ((LinkedHashMap<String, String>) loginResponse.getBody().getPayload()).get("token");
    assertNotNull(token);
    assertFalse(token.isBlank());
    assertFalse(token.isEmpty());
    return token;
  }
}
