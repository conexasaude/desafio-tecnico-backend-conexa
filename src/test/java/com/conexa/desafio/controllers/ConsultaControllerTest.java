package com.conexa.desafio.controllers;

import com.conexa.desafio.services.ConsultaService;
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

    mockMvc.perform(MockMvcRequestBuilders.post(ATTENDANCE_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(CONSULTA_VALIDA_REQUEST))
            .andExpect(status().isOk());
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
    doThrow(RuntimeException.class).when(consultaService).salvarConsulta(any());
    mockMvc.perform(MockMvcRequestBuilders.post(ATTENDANCE_ROUTE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(CONSULTA_VALIDA_REQUEST))
            .andExpect(status().isInternalServerError())
            .andReturn();
  }
}
