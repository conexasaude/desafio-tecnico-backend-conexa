package com.felipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.model.dto.v1.PasswordUpdateDTO;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.service.AuthService;
import com.felipe.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	AuthService service;

	@Autowired
	UserService userService;

	@GetMapping
	@Operation(summary = "Finds all Users", tags = {
			"User" }, description = "Retrieve a list of all users", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))) }),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<List<UserDTO>> findAllId() throws Exception {
		return ResponseEntity.ok(userService.findAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Find Doctor by ID", tags = {
			"Doctor" }, description = "Retrieve doctor details by providing a valid doctor ID",

			responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DoctorDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<UserDTO> findById(@PathVariable(value = "id") String id) throws Exception {
		return ResponseEntity.ok(userService.findById(id));
	}

	@PatchMapping("/{email}/password")
	@Operation(summary = "Update User Password", tags = {
			"User" }, description = "Update the password of an existing user by providing the user ID and the new password",

			responses = { @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<Void> updatePassword(@PathVariable(value = "email") String email,
			@Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
		service.changePassword(email, passwordUpdateDTO);
		return ResponseEntity.noContent().build();
	}

}
