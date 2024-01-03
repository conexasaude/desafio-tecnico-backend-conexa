package br.com.conexa.controllers;

import br.com.conexa.domain.user.AuthenticationDTO;
import br.com.conexa.domain.user.RegisterDTO;
import br.com.conexa.domain.user.User;
import br.com.conexa.services.AuthenticationService;
import br.com.conexa.utils.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static br.com.conexa.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private RegisterDTO registerDTO;
    private User newUser;
    private AuthenticationDTO authenticationDTO;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        registerDTO = new RegisterDTO(EMAIL,SENHA,SENHA,ESPECIALIDADE,CPF,DATA_NASCIMENTO,TELEFONE);
        newUser = new User(registerDTO.email(),
                Util.encryptedPassword(registerDTO),
                registerDTO.especialidade(),
                registerDTO.cpf(),
                registerDTO.dataNascimento(),
                registerDTO.telefone());
        authenticationDTO = new AuthenticationDTO(EMAIL,SENHA);

    }

    @Test
    void signup() {

        when(authenticationService.signup(registerDTO)).thenReturn(Optional.of(newUser));

        ResponseEntity responseEntity = authenticationController.signup(registerDTO);

        // Verificar se a resposta é 200 OK
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void login() {
        when(authenticationService.loginTokenGenerate(authenticationDTO)).thenReturn(anyString());

        ResponseEntity responseEntity = authenticationController.login(authenticationDTO);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void logoff() {
        String authorizationHeader = TOKEN;
        when(authenticationService.logoff(authorizationHeader)).thenReturn(ResponseEntity.ok("Logout bem-sucedido"));

        ResponseEntity responseEntity = authenticationController.logoff(authorizationHeader);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}