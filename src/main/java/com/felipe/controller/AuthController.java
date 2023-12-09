package com.felipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.model.dto.v1.CreateUserDoctorDTO;
import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.model.dto.v1.security.AccessTokenDTO;
import com.felipe.model.dto.v1.security.AccountCredentialsDTO;
import com.felipe.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/api/v1")
public class AuthController {

	@Autowired
	AuthService authService;

	@Operation(summary = "Login", tags = { "Authentication Endpoint" }, description = "Authenticates a user and returns a token", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AccessTokenDTO.class)) }),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content) })
	@PostMapping(value = "/login")
	public ResponseEntity<AccessTokenDTO> signin(@Valid @RequestBody AccountCredentialsDTO data) {
		return authService.signin(data);
	}

	@Operation(summary = "Refresh Token", tags = { "Authentication Endpoint" }, description = "Refresh token for an authenticated user and returns a new token", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AccessTokenDTO.class)) }),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content) })
	@PutMapping(value = "/refresh/{email}")
	public ResponseEntity<AccessTokenDTO> refresh(@PathVariable("email") String email,
			@RequestHeader("Authorization") String refreshToken, @Valid @RequestBody AccessTokenDTO dto) {
		return authService.refreshToken(email, refreshToken, dto);
	}

	@Operation(summary = "Signup", tags = {
			"User" }, description = "Create a user by providing doctor details in JSON or XML format", responses = {
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	@PostMapping(value = "/signup")
	public ResponseEntity<DoctorDTO> signup(@Valid @RequestBody CreateUserDoctorDTO data) throws Exception {
		return authService.signup(data);
	}

	@Operation(summary = "Logout", tags = { "Authentication Endpoint" }, description = "Logout an authenticated user", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content) })
	@PostMapping("/logout")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> logout(@Valid @RequestHeader("Authorization") String token) {
		return authService.logout(token);
	}

}
