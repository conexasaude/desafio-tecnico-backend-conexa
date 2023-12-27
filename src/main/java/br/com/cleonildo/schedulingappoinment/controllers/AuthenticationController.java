package br.com.cleonildo.schedulingappoinment.controllers;

import br.com.cleonildo.schedulingappoinment.dto.AuthenticationRequest;
import br.com.cleonildo.schedulingappoinment.dto.LoginResponse;
import br.com.cleonildo.schedulingappoinment.serivce.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok().body(this.service.loginService(request));
    }

    @PostMapping("/logoff")
    public ResponseEntity<LoginResponse> logoff() {
        return ResponseEntity.ok().build();
    }
}
