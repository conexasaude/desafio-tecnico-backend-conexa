package br.com.conexa.services;

import br.com.conexa.domain.user.AuthenticationDTO;
import br.com.conexa.domain.user.RegisterDTO;
import br.com.conexa.domain.user.User;
import br.com.conexa.infra.security.TokenService;
import br.com.conexa.repositories.UserRepository;
import br.com.conexa.utils.Util;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static br.com.conexa.constants.Constants.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenService tokenService;
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
    @DisplayName("Should login successfully when everything is OK")
    void signup() {
        when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.of(newUser));
        when(this.userRepository.save(newUser)).thenReturn(newUser);

        var userRegister = this.authenticationService.signup(registerDTO);
        verify(this.userRepository, times(1)).save(newUser);
        Assertions.assertThat(userRegister).isNotEmpty();
    }

    @Test
    @DisplayName("Should validate a method")
    void loginTokenNotGenerate() {
        Authentication auth = new UsernamePasswordAuthenticationToken(newUser, "password", newUser.getAuthorities());
        when(this.authenticationManager.authenticate(any())).thenReturn(auth);

        SecurityContextHolder.getContext().setAuthentication(auth);
        authenticationService.loginTokenGenerate(authenticationDTO);
        verify(this.tokenService, times(1)).generateToken(newUser);
    }

    @Test
    @DisplayName("Should logoff successfully when everything is OK")
    void logoff() {

        String token = TOKEN;

        when(this.tokenService.extractToken(anyString())).thenReturn(token);
        var msg = this.authenticationService.logoff(token);

        assert(msg.getStatusCode().is2xxSuccessful());
        verify(tokenService).extractToken(eq(TOKEN));

    }

    @Test
    @DisplayName("Should logoff badresquest")
    void logoffBadRequest() {

        String token = null;

        when(this.tokenService.extractToken(anyString())).thenReturn(token);
        var msg = this.authenticationService.logoff(token);

        assert(msg.getStatusCode().is4xxClientError());
    }

}