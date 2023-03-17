package com.conexa.desafio.controllers;

import com.conexa.desafio.models.ConsultaEntity;
import com.conexa.desafio.payload.ConsultaRequest;
import com.conexa.desafio.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ConsultaController {

  @Autowired private ConsultaService consultaService;

  @PostMapping(value = "/attendance", consumes = "application/json")
  public ResponseEntity<String> agendarConsulta(@RequestBody ConsultaRequest request) {
    try {
      ConsultaEntity consulta = request.convertToEntity();
      consultaService.salvarConsulta(consulta);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }
}
