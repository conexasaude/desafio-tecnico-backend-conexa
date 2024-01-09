package com.felipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<PagedModel<EntityModel<UserDTO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
			) throws Exception {
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "username"));
		return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Find User by ID", tags = {
			"User" }, description = "Retrieve user details by providing a valid user ID",

			responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<UserDTO> findById(@PathVariable(value = "id") String id) throws Exception {
		return ResponseEntity.ok(userService.findById(id));
	}
	
	@GetMapping("email/{email}")
	@Operation(summary = "Find User by email", tags = {
			"User" }, description = "Retrieve user details by providing a valid user email",

			responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<UserDTO> findByEmail(@PathVariable(value = "email") String email) throws Exception {
		return ResponseEntity.ok(userService.findByEmail(email));
	}
	
	@PatchMapping("/{id}/disable")
	@Operation(summary = "Disable User by ID", tags = {
			"User" }, description = "Retrieve user details by providing a valid user ID",

			responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<String> disableUser(@PathVariable(value = "id") String id) throws Exception {
		return ResponseEntity.ok(userService.disableUser(id));
	}

	@PatchMapping("/{id}/confirm-email")
	@Operation(summary = "Confirm Email of User by ID", tags = {
			"User" }, description = "Retrieve user details by providing a valid user ID",

			responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<String> confirmEmail(@PathVariable(value = "id") String id) throws Exception {
		return ResponseEntity.ok(userService.confirmEmail(id));
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
