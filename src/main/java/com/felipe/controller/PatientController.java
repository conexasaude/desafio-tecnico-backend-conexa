package com.felipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.model.dto.v1.PasswordUpdateDTO;
import com.felipe.model.dto.v1.PatientDTO;
import com.felipe.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/patient")
@Tag(name = "Patient", description = "Endpoints for managagin Patient")
public class PatientController {

	@Autowired
	private PatientService service;

	@GetMapping
	@Operation(summary = "Finds all Patients", tags = {
			"Patient" }, description = "Retrieve a list of all patients", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PatientDTO.class))) }),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<List<PatientDTO>> findAllId() throws Exception {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Find Patient by ID", tags = {
			"Patient" }, description = "Retrieve patient details by providing a valid patient ID",

			responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<PatientDTO> findById(@PathVariable(value = "id") String id) throws Exception {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);

	}

	@PostMapping
	@Operation(summary = "Create Patient", tags = {
			"Patient" }, description = "Update an existing patient by providing updated patient details in JSON or XML format",

			responses = { @ApiResponse(description = "Success", responseCode = "201", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public PatientDTO create(@RequestBody PatientDTO patientDTO) throws Exception {
		return service.create(patientDTO);
	}

	@PutMapping
	@Operation(summary = "Update Patient", tags = {
			"Patient" }, description = "Update an existing patient by providing updated patient details in JSON or XML format", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public PatientDTO update(@RequestBody PatientDTO patientDTO) throws Exception {
		return service.update(patientDTO);
	}

	@PatchMapping("/{id}/password")
	@Operation(summary = "Update Patient Password", tags = {
			"Patient" }, description = "Update the password of an existing patient by providing the patient ID and the new password",

			responses = { @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<Void> updatePassword(@PathVariable(value = "id") String id,
			@RequestBody PasswordUpdateDTO passwordUpdateDTO) {
		service.changePassword(id, passwordUpdateDTO);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Patient", tags = {
			"Patient" }, description = "Delete an existing patient by providing the patient ID",

			responses = { @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<?> delete(@PathVariable(value = "id") String id) throws Exception {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
