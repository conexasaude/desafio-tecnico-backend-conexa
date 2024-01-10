package com.felipe.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.model.dto.v1.AttendanceDTO;
import com.felipe.model.dto.v1.PatientDTO;
import com.felipe.service.AttendanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Attendance")
@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

	@Autowired
	AttendanceService service;

	@PostMapping()
	@Operation(summary = "Create an Attendance", tags = {
			"Attendance" }, description = "Create an attendance, if not exists attendance create too",

			responses = { @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<AttendanceDTO> updatePassword(@Valid @RequestBody AttendanceDTO dto) {
		AttendanceDTO savedAttendance = service.createAttendanceWithPatient(dto);
		return ResponseEntity.created(null).body(savedAttendance);
	}

	@GetMapping
	@Operation(summary = "Finds all Attendance", tags = {
			"Attendance" }, description = "Retrieve a list of all attendances", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AttendanceDTO.class))) }),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<PagedModel<EntityModel<AttendanceDTO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) throws Exception {
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "dateTime"));
		return ResponseEntity.ok(service.findAll(pageable));
	}

	@GetMapping("/findByDateTimeBetween")
	@Operation(summary = "Finds attendance by DateTime Between", tags = {
			"Patient" }, description = "Retrieve a list of all attendance", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PatientDTO.class))) }),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<PagedModel<EntityModel<AttendanceDTO>>> findByDateTimeBetween(
			@RequestParam(value = "initial_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime initialDate,
			@RequestParam(value = "end_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) throws Exception {
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
	    if (initialDate == null) {
	        initialDate = LocalDateTime.now().minusHours(24);
	    }

	    if (endDate == null) {
	        endDate = LocalDateTime.now();
	    }
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "dateTime"));
		return ResponseEntity.ok(service.findByDateTimeBetween(initialDate, endDate, pageable));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Find Attendance by ID", tags = {
			"Attendance" }, description = "Retrieve attendance details by providing a valid attendance ID",

			responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<AttendanceDTO> findById(@PathVariable(value = "id") String id) throws Exception {
		return ResponseEntity.ok(service.findById(id));

	}

	@PutMapping
	@Operation(summary = "Update Attendance", tags = {
			"Attendance" }, description = "Update an existing attendance by providing updated attendance details in JSON or XML format", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class)) }),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<AttendanceDTO> update(@RequestBody AttendanceDTO attendanceDTO) throws Exception {
		return ResponseEntity.ok(service.update(attendanceDTO));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Attendance", tags = {
			"Attendance" }, description = "Delete an existing attendance by providing the attendance ID",

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
