package br.com.cleonildo.schedulingappoinment.controllers;

import br.com.cleonildo.schedulingappoinment.dto.AttendanceRequest;
import br.com.cleonildo.schedulingappoinment.dto.AttendanceResponse;
import br.com.cleonildo.schedulingappoinment.dto.DoctorRequest;
import br.com.cleonildo.schedulingappoinment.dto.DoctorRequestNoPassword;
import br.com.cleonildo.schedulingappoinment.dto.DoctorResponse;
import br.com.cleonildo.schedulingappoinment.serivce.AttendanceService;
import br.com.cleonildo.schedulingappoinment.serivce.DoctortService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/attendance")
@Controller
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService service;


    @GetMapping(value = "/{id}")
    public ResponseEntity<AttendanceResponse> getAttendanceById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAttendanceById(id));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> getListDoctors() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllAttendances());
    }

    @PostMapping
    public ResponseEntity<AttendanceResponse> saveAttendance(@RequestBody @Valid AttendanceRequest request) {
        var response = this.service.saveAttendance(request);
        return ResponseEntity.ok().body(response);
    }
}
