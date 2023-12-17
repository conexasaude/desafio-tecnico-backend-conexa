package br.com.cleonildo.schedulingappoinment.controllers;

import br.com.cleonildo.schedulingappoinment.dto.PatientRequest;
import br.com.cleonildo.schedulingappoinment.dto.PatientResponse;
import br.com.cleonildo.schedulingappoinment.serivce.PatientService;
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
@RequestMapping(value = "/api/v1/patients")
@Controller
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPatientById(id));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getListPatients() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllPatients());
    }

    @PostMapping
    public ResponseEntity<PatientResponse> savePatient(@RequestBody @Valid PatientRequest request) {
        var response = this.service.savePatient(request);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id,
                                                        @RequestBody @Valid PatientRequest request) {

        var response = this.service.updatePatient(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PatientResponse> deletePatient(@PathVariable Long id) {
        this.service.deletePatientById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
