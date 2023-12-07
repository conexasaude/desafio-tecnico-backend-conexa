package com.felipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.model.dto.v1.PasswordUpdateDTO;
import com.felipe.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	AuthService service;
   
	@PatchMapping("/{email}/password")
	@Operation(summary = "Update User Password", tags = {
			"User" }, description = "Update the password of an existing user by providing the user ID and the new password",

			responses = { @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<Void> updatePassword(@PathVariable(value = "email") String email,
			@RequestBody PasswordUpdateDTO passwordUpdateDTO) {
		service.changePassword(email, passwordUpdateDTO);
		return ResponseEntity.noContent().build();
	}

}
