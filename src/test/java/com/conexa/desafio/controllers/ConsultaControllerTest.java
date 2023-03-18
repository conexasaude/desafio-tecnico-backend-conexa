package com.conexa.desafio.controllers;

import com.conexa.desafio.helpers.TestDataHelper;
import com.conexa.desafio.models.ConsultaEntity;
import com.conexa.desafio.models.PacienteEntity;
import com.conexa.desafio.services.ConsultaService;
import com.conexa.desafio.services.PacienteService;
import com.conexa.desafio.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.conexa.desafio.helpers.TestDataHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ConsultaController.class)
class ConsultaControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ConsultaController consultaController;

  @MockBean
  private ConsultaService consultaService;

  @MockBean
  private TokenService tokenService;

  @MockBean
  private PacienteService pacienteService;

  @BeforeEach
  void beforeEach() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders
            .standaloneSetup(consultaController)
            .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
            .build();
  }

  @Test
  void deveRetornarOkQuandoOsDadosDaConsultaForemValidos() throws Exception {

    doReturn(null).when(consultaService).salvarConsulta(any());
    doReturn(null).when(tokenService).buscarUsuarioPorToken(any());
    doReturn(false).when(pacienteService).pacienteJaExiste(any());

    mockMvc.perform(MockMvcRequestBuilders.post(ATTENDANCE_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(CONSULTA_VALIDA_REQUEST)
                    .header(HttpHeaders.AUTHORIZATION, TOKEN_TEST_COM_PREFIXO))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.payload.paciente.nome").value("João Castro"));
  }

  @Test
  void deveRetornarBadRequestQuandoAlgumDadoDaConsultaForInvalido() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(ATTENDANCE_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(CAMPO_INVALIDO_CONSULTA_REQUEST))
            .andExpect(status().isBadRequest())
            .andReturn();
  }

  @Test
  void deveRetornarBadRequestSeADataDaConsultaForAnteriorAoAgendamento() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(ATTENDANCE_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(DATA_CONSULTA_PASSADA_REQUEST))
            .andExpect(status().isBadRequest())
            .andReturn();
  }

  @Test
  void deveRetornarInternalServerErrorQuandoAlgumErroInesperadoOcorrer() throws Exception {
    doThrow(new RuntimeException("erro inesperado")).when(consultaService).salvarConsulta(any());
    mockMvc.perform(MockMvcRequestBuilders.post(ATTENDANCE_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(CONSULTA_VALIDA_REQUEST)
                    .header(HttpHeaders.AUTHORIZATION, TOKEN_TEST_COM_PREFIXO))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.code").value(500))
            .andExpect(jsonPath("$.message").value("erro inesperado"));
  }

  @Test
  void deveRetornarOPacienteEncontradoSeJaExistirRegistroNoBanco() throws Exception {
    doReturn(null).when(tokenService).buscarUsuarioPorToken(any());
    doReturn(true).when(pacienteService).pacienteJaExiste(any());
    PacienteEntity paciente = TestDataHelper.deserializeObject(DADOS_PACIENTE, PacienteEntity.class);
    doReturn(paciente).when(pacienteService).buscarPacientePorNome(any());
    mockMvc.perform(MockMvcRequestBuilders.post(ATTENDANCE_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(CONSULTA_VALIDA_REQUEST)
                    .header(HttpHeaders.AUTHORIZATION, TOKEN_TEST_COM_PREFIXO))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.payload.paciente.nome").value("Nome distinto"));
  }
}
