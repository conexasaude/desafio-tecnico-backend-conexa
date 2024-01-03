package br.com.conexa.services;

import br.com.conexa.domain.user.RegisterDTO;
import br.com.conexa.domain.user.User;
import br.com.conexa.repositories.UserRepository;
import br.com.conexa.utils.Util;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static br.com.conexa.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuthorizationServiceTest {
    //@Autowired
    @InjectMocks
    AuthorizationService authorizationService;

    @Autowired
    private EntityManager entityManager;

    @Mock
    private UserRepository userRepository;

    private User newUser;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        RegisterDTO registerDTO = new RegisterDTO(EMAIL, SENHA, SENHA, ESPECIALIDADE, CPF, DATA_NASCIMENTO, TELEFONE);
        newUser = new User(registerDTO.email(),
                Util.encryptedPassword(registerDTO),
                registerDTO.especialidade(),
                registerDTO.cpf(),
                registerDTO.dataNascimento(),
                registerDTO.telefone());

    }

    @Test
    void loadUserByUsername() {

        when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(newUser));
        UserDetails userDetails = this.authorizationService.loadUserByUsername(newUser.getEmail());
        assertNotNull(userDetails);
    }
}