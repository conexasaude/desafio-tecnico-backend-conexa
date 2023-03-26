package com.conexa.saude.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conexa.saude.model.dto.AttendanceDTO;
import com.conexa.saude.workflow.RegisterAttendanceWorkFlow;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AttendanceController {
    
    @Autowired
    private RegisterAttendanceWorkFlow registerPacientWorkFlow;


    @PostMapping("/attendance")
    public ResponseEntity<?> cadastrarMedico(@Valid @RequestBody AttendanceDTO attendanceDTO, Principal principal) {
        String usarname = principal.getName();
        
        attendanceDTO.setEmailDoctor(usarname);
        registerPacientWorkFlow.doExecute(attendanceDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
        
    }
}
