package com.juliagomes.desafiobackendconexa.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juliagomes.desafiobackendconexa.model.dto.AgendamentoDTO;
import com.juliagomes.desafiobackendconexa.services.AgendamentoService;

@RestController
@RequestMapping("/api/v1")
public class AgendamentoController {
	
	@Autowired
	AgendamentoService agendamentoService;
	
	@PostMapping("attendance")
	public ResponseEntity<?> save(@Valid @RequestBody AgendamentoDTO agendamento, @RequestHeader(value = "Authorization")String authorization) {
		agendamentoService.save(agendamento.toEntity(), authorization);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
