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
import com.felipe.model.dto.v1.security.AccessTokenDTO;
import com.felipe.model.dto.v1.security.AccountCredentialsDTO;
import com.felipe.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/api/v1")
public class AuthController {

	@Autowired
	AuthService authService;

	@Operation(summary = "Authenticates a user and return a token")
	@PostMapping(value = "/login")
	public ResponseEntity<AccessTokenDTO> signin(@RequestBody AccountCredentialsDTO data) {
		return authService.signin(data);
	}
	
	@Operation(summary = "Refresh token for authenticated user and returns a token")
	@PutMapping(value = "/refresh/{email}")
	public ResponseEntity<AccessTokenDTO> refresh(@PathVariable("email") String email, @RequestHeader("Authorization") String refreshToken, @RequestBody AccessTokenDTO dto) {
		return authService.refreshToken(email, refreshToken, dto);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/signup")
	@Operation(summary = "Create User", tags = {
	"Doctor" }, description = "Create an user by providing doctor details in JSON or XML format",

	responses = { @ApiResponse(description = "Success", responseCode = "201", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserDoctorDTO.class)) }),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity signup(@RequestBody CreateUserDoctorDTO data) throws Exception {
		return authService.signup(data);
	}
	
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        return authService.logout(token);
    }
    
}
