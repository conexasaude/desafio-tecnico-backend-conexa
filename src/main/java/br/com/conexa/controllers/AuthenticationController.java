package br.com.conexa.controllers;

import br.com.conexa.domain.user.AuthenticationDTO;
import br.com.conexa.domain.user.LoginResponseDTO;
import br.com.conexa.domain.user.RegisterDTO;
import br.com.conexa.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid RegisterDTO data){

        if(!Objects.equals(data.senha(), data.confirmacaoSenha())) return ResponseEntity.badRequest().build();

        final Optional<UserDetails> user = this.authenticationService.signup(data);
        if(user.isEmpty()) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){

        final String token =  this.authenticationService.loginTokenGenerate(data);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    //TODO o melhor mesmo seria realizar pelo front excluindo
    @PostMapping("/logoff")
    public ResponseEntity<String> logoff(@RequestHeader("Authorization") String authorizationHeader) {
        return authenticationService.logoff(authorizationHeader);
    }

}
