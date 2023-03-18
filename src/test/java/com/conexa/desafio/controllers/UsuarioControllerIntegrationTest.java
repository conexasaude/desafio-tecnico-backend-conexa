package com.conexa.desafio.controllers;

import com.conexa.desafio.helpers.TestDataHelper;
import com.conexa.desafio.payload.BaseResponse;
import com.conexa.desafio.payload.LoginRequest;
import com.conexa.desafio.payload.SignupRequest;
import com.conexa.desafio.security.JwtGenerator;
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
public class UsuarioControllerIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private JwtGenerator jwtGenerator;

  @Test
  void deveRetornarUmTokenValidoQuandoAsCredenciaisForemValidas() throws Exception {
    String token = cadastraEFazLogin();
    assertTrue(jwtGenerator.validarToken(token));
  }

  @Test
  void deveRealizarLogoffComSucessoQuandoReceberTokenValido() throws URISyntaxException {
    String token = cadastraEFazLogin();
    HttpEntity<String> entity = constroiHttpEntity(token, "body");
    ResponseEntity<BaseResponse> logoffResponse =
            restTemplate.exchange(LOGOFF_ROUTE, HttpMethod.POST, entity, BaseResponse.class);
    assertEquals(
            HttpStatusCode.valueOf(HttpStatus.OK.value()), logoffResponse.getStatusCode());
  }

  @Test
  void deveRetornarUnauthorizedQuandoOTokenForInvalido() {
    HttpEntity<String> entity = constroiHttpEntity(TOKEN_TEST_COM_PREFIXO, "body");
    ResponseEntity<BaseResponse> logoffResponse =
        restTemplate.exchange(LOGOFF_ROUTE, HttpMethod.POST, entity, BaseResponse.class);
    assertEquals(
        HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()), logoffResponse.getStatusCode());
  }

  @Test
  void deveRetornarUnauthorizedQuandoNaoInformarOToken() {
    HttpEntity<String> entity = constroiHttpEntity(null, "body");
    ResponseEntity<BaseResponse> logoffResponse =
            restTemplate.exchange(LOGOFF_ROUTE, HttpMethod.POST, entity, BaseResponse.class);
    assertEquals(
            HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()), logoffResponse.getStatusCode());
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
