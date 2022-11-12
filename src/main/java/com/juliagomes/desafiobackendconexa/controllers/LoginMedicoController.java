package com.juliagomes.desafiobackendconexa.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juliagomes.desafiobackendconexa.exception.ConexaDesafioAPIException;
import com.juliagomes.desafiobackendconexa.model.dto.JWTAuthResponse;
import com.juliagomes.desafiobackendconexa.model.dto.LoginDTO;
import com.juliagomes.desafiobackendconexa.model.dto.RegistroMedicoDTO;
import com.juliagomes.desafiobackendconexa.security.JwtTokenProvider;
import com.juliagomes.desafiobackendconexa.services.InvalidaJWTLogout;
import com.juliagomes.desafiobackendconexa.services.RegistroMedicoService;

@RestController
@RequestMapping("/api/v1")
public class LoginMedicoController {
		
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RegistroMedicoService medicoService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private InvalidaJWTLogout invalidaJWTLogout;

	@PostMapping("login")
	public ResponseEntity<JWTAuthResponse> authenticatedUser(@RequestBody LoginDTO loginDTO) {
		try {

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenProvider.generateToken(authentication);
			return ResponseEntity.ok(new JWTAuthResponse(token));
			
		} catch (BadCredentialsException e) {
			throw new ConexaDesafioAPIException(HttpStatus.BAD_REQUEST, "Usuário inexistente ou senha inválida");
		}

	}

	@PostMapping("signup")
	public ResponseEntity<?> registerUser(@RequestBody @Valid RegistroMedicoDTO registroMedicoDTO) {
		medicoService.save(registroMedicoDTO.toEntity());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("logoff")
	public ResponseEntity<?> logout(@RequestHeader(value = "Authorization")String authorization) {
		invalidaJWTLogout.addTokenBlackList(authorization);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
