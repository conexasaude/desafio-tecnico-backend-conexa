package com.conexa.desafio.controllers;

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
import java.time.LocalDate;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerIntegrationTest {
  // TODO: refatorar o código

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private JwtGenerator jwtGenerator;

  @Test
  void deveRetornarUmTokenValidoQuandoAsCredenciaisForemValidas() throws Exception {
    SignupRequest usuario =
        SignupRequest.builder()
            .email("medico@email.com")
            .senha("teste")
            .confirmacaoSenha("teste")
            .especialidade("Cardiologista")
            .cpf("101.202.303-11")
            .dataNascimento(LocalDate.now())
            .telefone("(21) 3232-6565")
            .build();
    ResponseEntity<BaseResponse> signupResponse =
        restTemplate.postForEntity(new URI("/api/v1/signup"), usuario, BaseResponse.class);
    assertEquals(
        HttpStatusCode.valueOf(HttpStatus.CREATED.value()), signupResponse.getStatusCode());
    LoginRequest loginRequest =
        LoginRequest.builder().email("medico@email.com").senha("teste").build();
    ResponseEntity<BaseResponse> loginResponse =
        restTemplate.postForEntity(new URI("/api/v1/login"), loginRequest, BaseResponse.class);
    assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), loginResponse.getStatusCode());
    String token = ((LinkedHashMap<String, String>) loginResponse.getBody().getPayload()).get("token");
    assertNotNull(token);
    assertFalse(token.isBlank());
    assertFalse(token.isEmpty());
    assertTrue(jwtGenerator.validarToken(token));
  }

  @Test
  void deveRetornarUnauthorizedQuandoOTokenForInvalido() {
    String testToken =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZWRpY29AZW1haWwuY29tIiwiaWF0IjoxNjc4OTgzNDgzLCJleHAiOjE2Nzg5ODQzODN9.7OzsjKb63COaau6X8mbO5N1xX6F0jpvnYGG2jRE-9sfDjWhrCe7SakBx5Hm2osr4YHanAqn2_YrplwL9sZwWhg";
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(testToken);
    HttpEntity<String> entity = new HttpEntity<>("body", headers);
    ResponseEntity<BaseResponse> logoffResponse =
        restTemplate.exchange("/api/v1/logoff", HttpMethod.POST, entity, BaseResponse.class);
    assertEquals(
        HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()), logoffResponse.getStatusCode());
  }
}
