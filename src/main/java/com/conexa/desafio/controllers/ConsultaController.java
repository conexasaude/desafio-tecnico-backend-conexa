package com.conexa.desafio.controllers;

import com.conexa.desafio.models.ConsultaEntity;
import com.conexa.desafio.payload.BaseResponse;
import com.conexa.desafio.payload.ConsultaRequest;
import com.conexa.desafio.payload.ConsultaResponse;
import com.conexa.desafio.services.ConsultaService;
import com.conexa.desafio.services.PacienteService;
import com.conexa.desafio.services.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ConsultaController {

  @Autowired private ConsultaService consultaService;

  @Autowired private TokenService tokenService;

  @Autowired
  private PacienteService pacienteService;

  @Value("${conexa.desafio.tokenPrefix}")
  String PREFIX;

  @PostMapping(value = "/attendance", consumes = "application/json")
  @Transactional
  public ResponseEntity<BaseResponse> agendarConsulta(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
      @RequestBody @Valid ConsultaRequest request) {
    try {
      ConsultaEntity consulta =
          request.convertToEntity(
              tokenService.buscarUsuarioPorToken(token.substring(PREFIX.length() + 1)));
      if (pacienteService.pacienteJaExiste(request.getPaciente().getNome())){
        consulta.setPaciente(pacienteService.buscarPacientePorNome(request.getPaciente().getNome()));
      }
      consultaService.salvarConsulta(consulta);
      return ResponseEntity.ok(BaseResponse.buildBaseResponse(HttpStatus.OK, ConsultaResponse.convertoToResponse(consulta)));
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(BaseResponse.buildBaseResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
  }
}
