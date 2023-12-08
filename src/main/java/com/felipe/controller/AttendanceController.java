package com.felipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.model.dto.v1.AttendanceDTO;
import com.felipe.service.AttendanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
			"Attendance" }, description = "Create an attendance, if not exists patient create too",

			responses = { @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<AttendanceDTO> updatePassword(@Valid @RequestBody AttendanceDTO dto) {
		return service.createAttendanceWithPatient(dto);
	}

}
