package com.conexa.desafio.controllers;

import com.conexa.desafio.models.UsuarioEntity;
import com.conexa.desafio.payload.LoginResponse;
import com.conexa.desafio.payload.UsuarioLogInDto;
import com.conexa.desafio.payload.UsuarioSignUpDto;
import com.conexa.desafio.security.JwtGenerator;
import com.conexa.desafio.services.TokenService;
import com.conexa.desafio.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private UsuarioController usuarioController;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private UsuarioService usuarioService;

    @Spy private AuthenticationManager authenticationManager;

    @Mock private JwtGenerator jwtGenerator;

    @Mock private TokenService tokenService;

    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(usuarioController, "PREFIX", "Bearer");
    }

    @Test
    public void deveRetornarSucessoQuandoARequisicaoForRealizadaComDadosValidos() throws Exception {
        UsuarioSignUpDto signUpRequest = UsuarioSignUpDto.builder()
                .email("test@email.com")
                .senha("test")
                .confirmacaoSenha("test")
                .cpf("111.111.111-11")
                .especialidade("teste")
                .dataNascimento(new Date())
                .build();

        doReturn(false).when(usuarioService).usuarioJaExiste(any());

        ResponseEntity<String> response = usuarioController.adicionaUsuario(signUpRequest);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), response.getStatusCode());
    }

    @Test
    public void deveRetornar400QuandoOUsuarioJaExiste() throws Exception {
        UsuarioSignUpDto signUpRequest = UsuarioSignUpDto.builder()
                .email("test@email.com")
                .senha("test")
                .confirmacaoSenha("test")
                .cpf("111.111.111-11")
                .especialidade("teste")
                .dataNascimento(new Date())
                .build();

        doReturn(true).when(usuarioService).usuarioJaExiste(any());

        ResponseEntity<String> response = usuarioController.adicionaUsuario(signUpRequest);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), response.getStatusCode());
    }

    @Test
    public void deveRetornar500QuandoAlgumErroInesperadoOcorrer() throws Exception {
        UsuarioSignUpDto signUpRequest = UsuarioSignUpDto.builder()
                .email("test@email.com")
                .senha("test")
                .confirmacaoSenha("test")
                .cpf("111.111.111-11")
                .especialidade("teste")
                .dataNascimento(new Date())
                .build();

        doReturn(false).when(usuarioService).usuarioJaExiste(any());
        doThrow(RuntimeException.class).when(modelMapper).map(any(), any());

        ResponseEntity<String> response = usuarioController.adicionaUsuario(signUpRequest);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), response.getStatusCode());
    }

    @Test
    public void deveRetornarOTokenQuandoRealizarOLoginComSucesso() throws Exception {
        UsuarioEntity usuarioEntity = UsuarioEntity.builder().build();

        UsuarioLogInDto loginRequest = UsuarioLogInDto.builder()
                .email("test@email.com")
                .senha("test")
                .build();

        doReturn("tokenValido").when(jwtGenerator).generateToken(any());
        doReturn(usuarioEntity).when(usuarioService).buscarPorEmail(any());
        doReturn(null).when(tokenService).salvarToken(any());

        ResponseEntity<LoginResponse> response = usuarioController.login(loginRequest);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), response.getStatusCode());
        assertNotNull(response.getBody().getToken());
        assertFalse(response.getBody().getToken().isBlank());
        assertFalse(response.getBody().getToken().isEmpty());
        assertEquals("tokenValido", response.getBody().getToken());
    }

    @Test
    public void deveRetornarUnauthorizedQuandoAsCredenciaisForemInvalidas() throws Exception {
        UsuarioEntity usuarioEntity = UsuarioEntity.builder().build();

        UsuarioLogInDto loginRequest = UsuarioLogInDto.builder()
                .email("test@email.com")
                .senha("test")
                .build();

        doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(any());
        doReturn("tokenValido").when(jwtGenerator).generateToken(any());
        doReturn(usuarioEntity).when(usuarioService).buscarPorEmail(any());
        doReturn(null).when(tokenService).salvarToken(any());

        ResponseEntity<LoginResponse> response = usuarioController.login(loginRequest);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()), response.getStatusCode());
    }

    @Test
    public void deveRetornarInternalServerErrorQuandoAlgumErroInesperadoAcontecer() throws Exception {
        UsuarioEntity usuarioEntity = UsuarioEntity.builder().build();

        UsuarioLogInDto loginRequest = UsuarioLogInDto.builder()
                .email("test@email.com")
                .senha("test")
                .build();

        doThrow(RuntimeException.class).when(authenticationManager).authenticate(any());

        ResponseEntity<LoginResponse> response = usuarioController.login(loginRequest);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), response.getStatusCode());
    }

    @Test
    public void deveRemoverOTokenComSucessoQuandoOTokenEhValido() throws Exception {
        String testToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZWRpY29AZW1haWwuY29tIiwiaWF0IjoxNjc4OTgzNDgzLCJleHAiOjE2Nzg5ODQzODN9.7OzsjKb63COaau6X8mbO5N1xX6F0jpvnYGG2jRE-9sfDjWhrCe7SakBx5Hm2osr4YHanAqn2_YrplwL9sZwWhg";

        doReturn(true).when(tokenService).tokenJaExiste(any());
        doNothing().when(tokenService).removerToken(any());

        ResponseEntity<String> response = usuarioController.logoff(testToken);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), response.getStatusCode());
    }

    @Test
    public void deveRetornar400QuandoOLogoutEhSolicitadoParaUmUsuarioQueNaoEstaLogado() throws Exception {
        String testToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZWRpY29AZW1haWwuY29tIiwiaWF0IjoxNjc4OTgzNDgzLCJleHAiOjE2Nzg5ODQzODN9.7OzsjKb63COaau6X8mbO5N1xX6F0jpvnYGG2jRE-9sfDjWhrCe7SakBx5Hm2osr4YHanAqn2_YrplwL9sZwWhg";

        doReturn(false).when(tokenService).tokenJaExiste(any());

        ResponseEntity<String> response = usuarioController.logoff(testToken);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), response.getStatusCode());
        assertEquals("O usuário não está logado!", response.getBody());
    }

    @Test
    public void deveRetornar500QuandoAlgumErroInesperadoOcorrerAoInvalidarOToken() throws Exception {
        String testToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZWRpY29AZW1haWwuY29tIiwiaWF0IjoxNjc4OTgzNDgzLCJleHAiOjE2Nzg5ODQzODN9.7OzsjKb63COaau6X8mbO5N1xX6F0jpvnYGG2jRE-9sfDjWhrCe7SakBx5Hm2osr4YHanAqn2_YrplwL9sZwWhg";

        doThrow(RuntimeException.class).when(tokenService).tokenJaExiste(any());

        ResponseEntity<String> response = usuarioController.logoff(testToken);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), response.getStatusCode());
    }
}