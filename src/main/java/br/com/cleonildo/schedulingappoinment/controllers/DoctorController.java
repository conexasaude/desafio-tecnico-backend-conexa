package br.com.cleonildo.schedulingappoinment.controllers;

import br.com.cleonildo.schedulingappoinment.dto.DoctorRequest;
import br.com.cleonildo.schedulingappoinment.dto.DoctorRequestNoPassword;
import br.com.cleonildo.schedulingappoinment.dto.DoctorResponse;
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
@RequestMapping(value = "/api/v1/doctors")
@Controller
@RequiredArgsConstructor
public class DoctorController {

    private final DoctortService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getDoctorById(id));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getListDoctors() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllDoctors());
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> saveDoctor(@RequestBody @Valid DoctorRequest request) {
        var response = this.service.saveDoctor(request);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DoctorResponse> updatePatient(@PathVariable Long id,
                                                        @RequestBody @Valid DoctorRequestNoPassword request) {

        var response = this.service.updateDoctor(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        this.service.deleteDoctortById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
