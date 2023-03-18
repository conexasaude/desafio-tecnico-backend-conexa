package com.conexa.desafio.controllers;

import com.conexa.desafio.security.JwtGenerator;
import com.conexa.desafio.services.TokenService;
import com.conexa.desafio.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.conexa.desafio.helpers.TestDataHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private UsuarioController usuarioController;

  @MockBean private ModelMapper modelMapper;

  @MockBean private UsuarioService usuarioService;

  @MockBean private AuthenticationManager authenticationManager;

  @MockBean private JwtGenerator jwtGenerator;

  @MockBean private TokenService tokenService;

  @BeforeEach
  void beforeEach() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(usuarioController, "PREFIX", "Bearer");
    mockMvc =
        MockMvcBuilders.standaloneSetup(usuarioController)
            .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
            .build();
  }

  @Test
  public void deveRetornarSucessoQuandoARequisicaoForRealizadaComDadosValidos() throws Exception {
    doReturn(false).when(usuarioService).usuarioJaExiste(any());

    mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_ROUTE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(SIGNUP_VALIDO_REQUEST))
            .andExpect(status().isCreated());
  }

  @Test
  public void deveRetornarBadRequestQuandoOUsuarioJaExiste() throws Exception {
    doReturn(true).when(usuarioService).usuarioJaExiste(any());
    mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(SIGNUP_VALIDO_REQUEST))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void deveRetornarInternalServerErrorQuandoAlgumErroInesperadoOcorrer() throws Exception {
    doReturn(false).when(usuarioService).usuarioJaExiste(any());
    doThrow(RuntimeException.class).when(modelMapper).map(any(), any());
    mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(SIGNUP_VALIDO_REQUEST))
            .andExpect(status().isInternalServerError());
  }

  @Test
  public void deveRetornarBadRequestQuandoAsSenhasForemDistintas() throws Exception {
    doReturn(false).when(usuarioService).usuarioJaExiste(any());
    mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(SIGNUP_SENHAS_DISTINTAS_REQUEST))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value(400))
            .andExpect(jsonPath("$.message").value("As senhas não são iguais!"));
  }

  @Test
  public void deveRetornarBadRequestQuandoAlgumCampoForInvalido() throws Exception {
    doReturn(false).when(usuarioService).usuarioJaExiste(any());
    mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(CAMPOS_INVALIDOS_SIGNUP_REQUEST))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void deveRetornarBadRequestQuandoOCpfForInvalido() throws Exception {
    doReturn(false).when(usuarioService).usuarioJaExiste(any());
    mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(CPF_INVALIDO_SIGNUP_REQUEST))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void deveRetornarBadRequestQuandoADataDeNascimentoForMaiorQueODiaDoCadastro() throws Exception {
    doReturn(false).when(usuarioService).usuarioJaExiste(any());
    mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(DATA_NASCIMENTO_INVALIDA_SIGNUP_REQUEST))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void deveRetornarBadRequestQuandoOTelefoneForInvalido() throws Exception {
    doReturn(false).when(usuarioService).usuarioJaExiste(any());
    mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TELEFONE_INVALIDO_SIGNUP_REQUEST))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void deveRetornarOTokenQuandoRealizarOLoginComSucesso() throws Exception {
    doReturn("tokenValido").when(jwtGenerator).generateToken(any());
    doReturn(null).when(usuarioService).buscarPorEmail(any());
    doNothing().when(tokenService).removerTokenDoUsuario(any());
    doReturn(null).when(tokenService).salvarToken(any());
    mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(LOGIN_VALIDO_REQUEST))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.payload.token").value("tokenValido"));
  }

  @Test
  public void deveRetornarUnauthorizedQuandoAsCredenciaisForemInvalidas() throws Exception {
    doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(any());
    mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(SENHA_INVALIDA_LOGIN_REQUEST))
            .andExpect(status().isUnauthorized());
  }

  @Test
  public void deveRetornarInternalServerErrorQuandoAlgumErroInesperadoAcontecer() throws Exception {
    doThrow(new RuntimeException("erro inesperado")).when(authenticationManager).authenticate(any());
    mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(SENHA_INVALIDA_LOGIN_REQUEST))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.code").value(500))
            .andExpect(jsonPath("$.message").value("erro inesperado"));
  }

  @Test
  public void deveRetornarBadRequestQuandoAlgumCampoDoLoginForInvalido() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(CAMPOS_INVALIDOS_LOGIN_REQUEST))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void deveRemoverOTokenComSucessoQuandoOTokenEhValido() throws Exception {
    doReturn(true).when(tokenService).tokenJaExiste(any());
    doNothing().when(tokenService).removerToken(any());
    mockMvc.perform(MockMvcRequestBuilders.post(LOGOFF_ROUTE)
                    .header(HttpHeaders.AUTHORIZATION, TOKEN_TEST_COM_PREFIXO))
            .andExpect(status().isOk());
  }

  @Test
  public void deveRetornarInternalServerErrorQuandoAlgumErroInesperadoOcorrerAoInvalidarOToken() throws Exception {
    doThrow(new RuntimeException("erro inesperado")).when(tokenService).removerToken(any());
    mockMvc.perform(MockMvcRequestBuilders.post(LOGOFF_ROUTE)
                    .header(HttpHeaders.AUTHORIZATION, TOKEN_TEST_COM_PREFIXO))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.code").value(500))
            .andExpect(jsonPath("$.message").value("erro inesperado"));
  }
//
//  @Test
//  public void deveRetornarUnauthorizedQuandoSolicitaLogoffSemToken() throws Exception {
//    mockMvc.perform(MockMvcRequestBuilders.post(LOGOFF_ROUTE))
//            .andExpect(status().isUnauthorized());
//  }
}
