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

import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.model.dto.v1.PasswordUpdateDTO;
import com.felipe.service.DoctorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/doctor")
@Tag(name = "Doctor", description = "Endpoints for managagin Doctor")
public class DoctorController {

	@Autowired
	private DoctorService service;

	@GetMapping
	@Operation(summary = "Finds all Doctors", tags = {
			"Doctor" }, description = "Retrieve a list of all doctors", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DoctorDTO.class))) }),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<List<DoctorDTO>> findAllId() throws Exception {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
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
	public ResponseEntity<DoctorDTO> findById(@PathVariable(value = "id") String id) throws Exception {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);

	}

//	@PostMapping
//	@Operation(summary = "Create Doctor", tags = {
//			"Doctor" }, description = "Update an existing doctor by providing updated doctor details in JSON or XML format",
//
//			responses = { @ApiResponse(description = "Success", responseCode = "201", content = {
//					@Content(mediaType = "application/json", schema = @Schema(implementation = DoctorDTO.class)) }),
//					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
//					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
//					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
//	public DoctorDTO create(@RequestBody DoctorDTO doctorDTO) throws Exception {
//		return service.create(doctorDTO);
//	}

	@PutMapping
	@Operation(summary = "Update Doctor", tags = {
			"Doctor" }, description = "Update an existing doctor by providing updated doctor details in JSON or XML format", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = DoctorDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public DoctorDTO update(@RequestBody DoctorDTO doctorDTO) throws Exception {
		return service.update(doctorDTO);
	}

//	@PatchMapping("/{id}/password")
//	@Operation(summary = "Update Doctor Password", tags = {
//			"Doctor" }, description = "Update the password of an existing doctor by providing the doctor ID and the new password",
//
//			responses = { @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
//					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
//					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
//					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
//	public ResponseEntity<Void> updatePassword(@PathVariable(value = "id") String id,
//			@RequestBody PasswordUpdateDTO passwordUpdateDTO) {
//		service.changePassword(id, passwordUpdateDTO);
//		return ResponseEntity.noContent().build();
//	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Doctor", tags = {
			"Doctor" }, description = "Delete an existing doctor by providing the doctor ID",

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
