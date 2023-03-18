package com.conexa.desafio.controllers;

import com.conexa.desafio.models.TokenEntity;
import com.conexa.desafio.models.UsuarioEntity;
import com.conexa.desafio.payload.BaseResponse;
import com.conexa.desafio.payload.LoginResponse;
import com.conexa.desafio.payload.LoginRequest;
import com.conexa.desafio.payload.SignupRequest;
import com.conexa.desafio.security.JwtGenerator;
import com.conexa.desafio.services.TokenService;
import com.conexa.desafio.services.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
  public ResponseEntity<BaseResponse> adicionaUsuario(
      @RequestBody @Valid SignupRequest signupRequest) {
    if (usuarioService.usuarioJaExiste(signupRequest.getEmail())) {
      return ResponseEntity.badRequest()
          .body(BaseResponse.buildBaseResponse(HttpStatus.BAD_REQUEST));
    }
    try {
      if (!signupRequest.getSenha().equals(signupRequest.getConfirmacaoSenha())) {
        return ResponseEntity.badRequest()
            .body(
                BaseResponse.buildBaseResponse(
                    HttpStatus.BAD_REQUEST, "As senhas não são iguais!"));
      }
      UsuarioEntity usuarioEntityRequest = modelMapper.map(signupRequest, UsuarioEntity.class);
      usuarioService.criaUsuario(usuarioEntityRequest);
      return new ResponseEntity<>(
          BaseResponse.buildBaseResponse(HttpStatus.CREATED), HttpStatus.CREATED);
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(BaseResponse.buildBaseResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
  }

  @PostMapping("/login")
  @Transactional
  public ResponseEntity<BaseResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequest.getEmail(), loginRequest.getSenha()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String token = jwtGenerator.generateToken(authentication);
      UsuarioEntity usuario = usuarioService.buscarPorEmail(loginRequest.getEmail());
      TokenEntity tokenEntity = TokenEntity.builder().usuario(usuario).token(token).build();
      tokenService.removerTokenDoUsuario(usuario);
      tokenService.salvarToken(tokenEntity);
      return ResponseEntity.ok(
          BaseResponse.buildBaseResponse(HttpStatus.OK, new LoginResponse(token)));
    } catch (BadCredentialsException badCredentialsException) {
      return new ResponseEntity<>(
          BaseResponse.buildBaseResponse(HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(BaseResponse.buildBaseResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
  }

  @PostMapping("/logoff")
  @Transactional
  public ResponseEntity<BaseResponse> logoff(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    try {
      tokenService.removerToken(token.substring(PREFIX.length() + 1));
      return ResponseEntity.ok(BaseResponse.buildBaseResponse(HttpStatus.OK));
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(BaseResponse.buildBaseResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
  }
}
