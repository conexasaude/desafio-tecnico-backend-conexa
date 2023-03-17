package com.conexa.desafio.controllers;

import com.conexa.desafio.models.TokenEntity;
import com.conexa.desafio.models.UsuarioEntity;
import com.conexa.desafio.payload.LoginResponse;
import com.conexa.desafio.payload.UsuarioLogInDto;
import com.conexa.desafio.payload.UsuarioSignUpDto;
import com.conexa.desafio.security.JwtGenerator;
import com.conexa.desafio.services.TokenService;
import com.conexa.desafio.services.UsuarioService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

  @Autowired private ModelMapper modelMapper;

  @Autowired private UsuarioService usuarioService;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtGenerator jwtGenerator;

  @Autowired private TokenService tokenService;

  @Value("${conexa.desafio.tokenPrefix}")
  String PREFIX;

  @PostMapping(value = "/signup", consumes = "application/json")
  public ResponseEntity<String> adicionaUsuario(@RequestBody UsuarioSignUpDto usuarioSignUpDto) {
    if (usuarioService.usuarioJaExiste(usuarioSignUpDto.getEmail())) {
      return ResponseEntity.badRequest().body("O usuário já existe!");
    }
    try{
      UsuarioEntity usuarioEntityRequest = modelMapper.map(usuarioSignUpDto, UsuarioEntity.class);
      usuarioService.criaUsuario(usuarioEntityRequest);
      return ResponseEntity.created(null).build();
    }catch (Exception e){
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody UsuarioLogInDto usuarioLogInDto) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  usuarioLogInDto.getEmail(), usuarioLogInDto.getSenha()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String token = jwtGenerator.generateToken(authentication);
      UsuarioEntity usuario = usuarioService.buscarPorEmail(usuarioLogInDto.getEmail());
      TokenEntity tokenEntity = TokenEntity.builder().usuario(usuario).token(token).build();
      tokenService.salvarToken(tokenEntity);
      return ResponseEntity.ok().body(new LoginResponse(token));
    } catch (BadCredentialsException badCredentialsException){
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } catch (Exception e){
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/logoff")
  @Transactional
  public ResponseEntity<String> logoff(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    try {
      tokenService.removerToken(token.substring(PREFIX.length() + 1));
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }
}
