package com.conexa.desafio.controllers;

import com.conexa.desafio.payload.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConsultaControllerIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void deveAgendarConsultaComSucessoQuandoOTokenForValido()
      throws URISyntaxException, JsonProcessingException {
    SignupRequest usuario =
        SignupRequest.builder()
            .email("medico@email.com")
            .senha("teste")
            .confirmacaoSenha("teste")
            .especialidade("Cardiologista")
            .cpf("704.377.720-80")
            .dataNascimento(LocalDate.now().minusYears(40))
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
    String token =
        ((LinkedHashMap<String, String>) loginResponse.getBody().getPayload()).get("token");
    assertNotNull(token);
    assertFalse(token.isBlank());
    assertFalse(token.isEmpty());
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);
    ConsultaRequest consultaRequest =
        ConsultaRequest.builder()
            .dataHora(LocalDateTime.now().plusDays(10))
            .paciente(
                ConsultaRequest.Paciente.builder()
                    .nome("Paciente de teste")
                    .cpf("432.576.720-71")
                    .build())
            .build();
    HttpEntity<String> entity =
        new HttpEntity<>(objectMapper.writeValueAsString(consultaRequest), headers);
    ResponseEntity<String> consultaResponse =
        restTemplate.exchange("/api/v1/attendance", HttpMethod.POST, entity, String.class);
    assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), consultaResponse.getStatusCode());
  }

  @Test
  void deveRetornarErroQuandoTentarAgendarUmaConsultaComTokenInvalido()
      throws JsonProcessingException {
    String testToken =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZWRpY29AZW1haWwuY29tIiwiaWF0IjoxNjc4OTgzNDgzLCJleHAiOjE2Nzg5ODQzODN9.7OzsjKb63COaau6X8mbO5N1xX6F0jpvnYGG2jRE-9sfDjWhrCe7SakBx5Hm2osr4YHanAqn2_YrplwL9sZwWhg";
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(testToken);
    headers.setContentType(MediaType.APPLICATION_JSON);
    ConsultaRequest consultaRequest =
        ConsultaRequest.builder()
            .dataHora(LocalDateTime.now().plusDays(10))
            .paciente(
                ConsultaRequest.Paciente.builder()
                    .nome("Paciente de teste")
                    .cpf("432.576.720-71")
                    .build())
            .build();
    HttpEntity<String> entity =
        new HttpEntity<>(objectMapper.writeValueAsString(consultaRequest), headers);
    ResponseEntity<String> consultaResponse =
        restTemplate.exchange("/api/v1/attendance", HttpMethod.POST, entity, String.class);
    assertEquals(
        HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()), consultaResponse.getStatusCode());
  }
}
