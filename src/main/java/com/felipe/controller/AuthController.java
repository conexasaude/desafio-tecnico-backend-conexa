package com.felipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.model.dto.v1.security.AccountCredentialsDTO;
import com.felipe.model.dto.v1.security.CreateUserDoctorDTO;
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
		return authService.signin(data);
	}
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Refresh token for authenticated user and returns a token")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity refresh(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
		return authService.refreshToken(username, refreshToken);
	}

	@SuppressWarnings("rawtypes")
	@Operation(summary = "Create a new user")
	@PostMapping(value = "/signup")
	public ResponseEntity signup(@RequestBody CreateUserDoctorDTO data) throws Exception {
		return authService.signup(data);
	}
}
