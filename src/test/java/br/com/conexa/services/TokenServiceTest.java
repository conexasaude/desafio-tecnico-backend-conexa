package br.com.conexa.services;

import br.com.conexa.domain.user.RegisterDTO;
import br.com.conexa.domain.user.User;
import br.com.conexa.infra.security.TokenService;
import br.com.conexa.utils.Util;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static br.com.conexa.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TokenServiceTest {


    @Autowired
    @InjectMocks
    private TokenService tokenService;

    private User user;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        RegisterDTO registerDTO = new RegisterDTO(EMAIL, SENHA, SENHA, ESPECIALIDADE, CPF, DATA_NASCIMENTO, TELEFONE);
        user = new User(registerDTO.email(),
                Util.encryptedPassword(registerDTO),
                registerDTO.especialidade(),
                registerDTO.cpf(),
                registerDTO.dataNascimento(),
                registerDTO.telefone());
    }

    @Test
    @DisplayName("Should return a token")
    void generateToken() {
        String token = this.tokenService.generateToken(user);
        Assertions.assertThat(token).isNotEmpty();
    }

    @Test
    @DisplayName("Should return a exception object")
    void generateTokenWithException() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            tokenService.generateToken(null);
        });
        assertTrue(exception.getMessage().contains("User não pode ser vazio"));
    }

    @Test
    @DisplayName("Should return a exception object")
    void extractToken() {
        String token = TOKEN;

        var tokenExtract = this.tokenService.extractToken("Bearer "+token);
        Assertions.assertThat(token).isEqualTo(tokenExtract);

    }

    @Test
    @DisplayName("Should get a invalidate a token")
    void isTokenInvalid() {
        String token = TOKEN;

        this.tokenService.invalidateToken(token);
        var tokenInvalid = this.tokenService.isTokenInvalid(token);

        Assertions.assertThat(tokenInvalid).isTrue();

    }

    @Test
    @DisplayName("Should get a invalidate a token")
    void valid() {

        String user = this.tokenService.validateToken(TOKEN);

        Assertions.assertThat(user).isEqualTo(EMAIL);
    }


}