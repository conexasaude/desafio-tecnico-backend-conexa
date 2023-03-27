package com.conexa.saude.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conexa.saude.model.dto.DoctorDTO;
import com.conexa.saude.workflow.SaveDoctorWorkFlow;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class DoctorRegistrationController {

    @Autowired
    private SaveDoctorWorkFlow saveDoctorWorkflow;

    @PostMapping("/signup")
    public ResponseEntity<?> cadastrarMedico(@Valid @RequestBody DoctorDTO doctorDTO) {
       
        saveDoctorWorkflow.doExecute(doctorDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
