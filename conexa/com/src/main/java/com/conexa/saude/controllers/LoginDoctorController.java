package com.conexa.saude.controllers;

import com.conexa.saude.model.dto.JwtDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conexa.saude.model.dto.LoginDTO;
import com.conexa.saude.workflow.LogOffWorkFlow;
import com.conexa.saude.workflow.LoginDoctorWorkFlow;

import jakarta.validation.Valid;

import static com.conexa.saude.constants.ServiceConstants.*;

@RestController
@RequestMapping("/api/v1")
public class LoginDoctorController {

    @Autowired
    private LoginDoctorWorkFlow loginDoctorWorkflow;

    @Autowired 
    private LogOffWorkFlow logOffWorkFlow;
    
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = loginDoctorWorkflow.doExecute(loginDTO);
       
        return ResponseEntity.ok(new JwtDto(token));
       
    }

       
	@RequestMapping(value = "/logoff", method = RequestMethod.POST)
    public ResponseEntity<Void> logoff(@RequestHeader(name = AUTHORIZATION) String token) {

        logOffWorkFlow.doExecute(token);

        return ResponseEntity.ok(null);

    }
}
