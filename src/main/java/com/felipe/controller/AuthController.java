package com.felipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.exceptions.ForbbidenException;
import com.felipe.model.dto.v1.security.AccountCredentialsDTO;
import com.felipe.model.dto.v1.security.TokenDTO;
import com.felipe.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/api/v1")
public class AuthController {

	@Autowired
	AuthService authService;

	@SuppressWarnings("rawtypes")
	@Operation(summary = "Authenticates a user and return a token")
	@PostMapping(value = "/signin")
	public ResponseEntity signin(@RequestBody AccountCredentialsDTO data) {
		if (checkParamsIsNotNull(data)) {
			throw new ForbbidenException("Invalid client request!");
		}
		
		var token = authService.signin(data);
		if (token == null) {
			throw new ForbbidenException("Invalid client request!");
		}
		return token;
		
	}

	private boolean checkParamsIsNotNull(AccountCredentialsDTO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank() || data.getPassword() == null
				|| data.getPassword().isBlank();
	}
}
