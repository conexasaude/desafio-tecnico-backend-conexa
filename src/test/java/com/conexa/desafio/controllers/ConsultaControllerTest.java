package com.conexa.desafio.controllers;

import com.conexa.desafio.payload.ConsultaRequest;
import com.conexa.desafio.services.ConsultaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ActiveProfiles("test")
class ConsultaControllerTest {

    @InjectMocks
    private ConsultaController consultaController;

    @Spy
    private ConsultaService consultaService;


    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarSucessoQuandoARequisicaoForValida(){
        ConsultaRequest request = ConsultaRequest.builder()
                .dataHora(LocalDateTime.now().plusDays(10))
                .paciente(ConsultaRequest.Paciente.builder()
                        .nome("João Castro")
                        .cpf("101.202.303")
                        .build())
                .build();

        ResponseEntity<String> response = consultaController.agendarConsulta(request);

        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), response.getStatusCode());
    }

    @Test
    void deveRetornarInternalServerErroQuandoAlgumErroInesperadoOcorrer(){
        ConsultaRequest request = ConsultaRequest.builder()
                .dataHora(LocalDateTime.now().plusDays(10))
                .paciente(ConsultaRequest.Paciente.builder()
                        .nome("João Castro")
                        .cpf("101.202.303")
                        .build())
                .build();

        doThrow(RuntimeException.class).when(consultaService).salvarConsulta(any());

        ResponseEntity<String> response = consultaController.agendarConsulta(request);

        assertEquals(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), response.getStatusCode());
    }

}