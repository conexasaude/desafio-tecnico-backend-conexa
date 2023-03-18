package com.conexa.desafio.controllers;

import com.conexa.desafio.models.ConsultaEntity;
import com.conexa.desafio.payload.BaseResponse;
import com.conexa.desafio.payload.ConsultaRequest;
import com.conexa.desafio.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ConsultaController {

  @Autowired
  private ConsultaService consultaService;


  @PostMapping(value = "/attendance", consumes = "application/json")
  public ResponseEntity<BaseResponse> agendarConsulta(@RequestBody @Valid ConsultaRequest request) {
    try {
      ConsultaEntity consulta = request.convertToEntity();
      consultaService.salvarConsulta(consulta);
      return ResponseEntity.ok(BaseResponse.buildBaseResponse(HttpStatus.OK));
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(BaseResponse.buildBaseResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
  }
}
