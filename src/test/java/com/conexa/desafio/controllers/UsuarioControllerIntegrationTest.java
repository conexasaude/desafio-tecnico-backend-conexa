package com.conexa.desafio.controllers;

import com.conexa.desafio.payload.LoginResponse;
import com.conexa.desafio.payload.UsuarioLogInDto;
import com.conexa.desafio.payload.UsuarioSignUpDto;
import com.conexa.desafio.security.JwtGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerIntegrationTest {
    //TODO: refatorar o código

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Test
    void deveCriarUsuarioQuandoAsCredenciaisForemValidas() throws Exception {
        UsuarioSignUpDto usuario = UsuarioSignUpDto.builder()
                .email("medico@email.com")
                .senha("teste")
                .confirmacaoSenha("teste")
                .especialidade("Cardiologista")
                .cpf("101.202.303-11")
                .dataNascimento(new Date())
                .telefone("(21) 3232-6565")
                .build();
        ResponseEntity<String> response = restTemplate.postForEntity(new URI("/api/v1/signup"),
                usuario, String.class);

        assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), response.getStatusCode());
    }

    @Test
    void deveRetornarBadRequestQuandoUsuarioJaExiste() throws Exception {
        //TODO: implementar validacao de senha, cpf e telefone na request
        UsuarioSignUpDto usuario = UsuarioSignUpDto.builder()
                .email("medico@email.com")
                .senha("teste")
                .confirmacaoSenha("teste")
                .especialidade("Cardiologista")
                .cpf("101.202.303-11")
                .dataNascimento(new Date())
                .telefone("(21) 3232-6565")
                .build();
        ResponseEntity<String> response1 = restTemplate.postForEntity(new URI("/api/v1/signup"),
                usuario, String.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), response1.getStatusCode());
        ResponseEntity<String> response2 = restTemplate.postForEntity(new URI("/api/v1/signup"),
                usuario, String.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), response2.getStatusCode());
        assertEquals("O usuário já existe!", response2.getBody());
    }

    @Test
    void deveRetornarUmTokenValidoQuandoAsCredenciaisForemValidas() throws Exception {
        UsuarioSignUpDto usuario = UsuarioSignUpDto.builder()
                .email("medico@email.com")
                .senha("teste")
                .confirmacaoSenha("teste")
                .especialidade("Cardiologista")
                .cpf("101.202.303-11")
                .dataNascimento(new Date())
                .telefone("(21) 3232-6565")
                .build();
        ResponseEntity<String> signupResponse = restTemplate.postForEntity(new URI("/api/v1/signup"),
                usuario, String.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), signupResponse.getStatusCode());
        UsuarioLogInDto loginRequest = UsuarioLogInDto.builder()
                .email("medico@email.com")
                .senha("teste")
                .build();
        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(new URI("/api/v1/login"),
                loginRequest, LoginResponse.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody().getToken());
        assertFalse(loginResponse.getBody().getToken().isBlank());
        assertFalse(loginResponse.getBody().getToken().isEmpty());
        assertTrue(jwtGenerator.validarToken(loginResponse.getBody().getToken()));
    }

    @Test
    void deveRetornarUnauthorizedQuandoAsCredenciaisForemInvalidas() throws Exception {
        UsuarioSignUpDto usuario = UsuarioSignUpDto.builder()
                .email("medico@email.com")
                .senha("teste1")
                .confirmacaoSenha("teste1")
                .especialidade("Cardiologista")
                .cpf("101.202.303-11")
                .dataNascimento(new Date())
                .telefone("(21) 3232-6565")
                .build();
        ResponseEntity<String> signupResponse = restTemplate.postForEntity(new URI("/api/v1/signup"),
                usuario, String.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), signupResponse.getStatusCode());
        UsuarioLogInDto loginRequest = UsuarioLogInDto.builder()
                .email("medico@email.com")
                .senha("teste")
                .build();
        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(new URI("/api/v1/login"),
                loginRequest, LoginResponse.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()), loginResponse.getStatusCode());
    }

    @Test
    void deveDeslogarUsuarioQuandoOTokenForValido() throws Exception {
        UsuarioSignUpDto usuario = UsuarioSignUpDto.builder()
                .email("medico@email.com")
                .senha("teste")
                .confirmacaoSenha("teste")
                .especialidade("Cardiologista")
                .cpf("101.202.303-11")
                .dataNascimento(new Date())
                .telefone("(21) 3232-6565")
                .build();
        ResponseEntity<String> signupResponse = restTemplate.postForEntity(new URI("/api/v1/signup"),
                usuario, String.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), signupResponse.getStatusCode());
        UsuarioLogInDto loginRequest = UsuarioLogInDto.builder()
                .email("medico@email.com")
                .senha("teste")
                .build();
        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(new URI("/api/v1/login"),
                loginRequest, LoginResponse.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody().getToken());
        assertFalse(loginResponse.getBody().getToken().isBlank());
        assertFalse(loginResponse.getBody().getToken().isEmpty());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginResponse.getBody().getToken());
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> logoffResponse =
                restTemplate.exchange("/api/v1/logoff", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), logoffResponse.getStatusCode());
    }

    @Test
    void deveRetornarBadRequestQuandoOTokenForInvalido() {
        String testToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZWRpY29AZW1haWwuY29tIiwiaWF0IjoxNjc4OTgzNDgzLCJleHAiOjE2Nzg5ODQzODN9.7OzsjKb63COaau6X8mbO5N1xX6F0jpvnYGG2jRE-9sfDjWhrCe7SakBx5Hm2osr4YHanAqn2_YrplwL9sZwWhg";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(testToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> logoffResponse =
                restTemplate.exchange("/api/v1/logoff", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), logoffResponse.getStatusCode());
    }

}
