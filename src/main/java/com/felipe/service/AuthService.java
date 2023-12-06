package com.felipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.felipe.exceptions.ForbbidenException;
import com.felipe.model.dto.v1.security.AccountCredentialsDTO;
import com.felipe.model.dto.v1.security.TokenDTO;
import com.felipe.repositories.UserRepository;
import com.felipe.security.jwt.JwtTokenProvider;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserRepository repository;

	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsDTO data) {
		try {
			checkParamsIsNotNull(data);

			var username = data.getUsername();
			var password = data.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			var user = repository.findByUserName(username);

			var tokenResponse = new TokenDTO();

			if (user != null) {
				tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			validateTokenResponse(tokenResponse);

			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
		checkParamsIsNotNull(username, refreshToken);

		var user = repository.findByUserName(username);

		var tokenResponse = new TokenDTO();
		if (user != null) {
			tokenResponse = jwtTokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		validateTokenResponse(tokenResponse);
		
		return ResponseEntity.ok(tokenResponse);
	}

	private void checkParamsIsNotNull(String username, String refreshToken) {
		if (refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank()) {
			throw new ForbbidenException("Invalid client request!");
		}
	}

	private void checkParamsIsNotNull(AccountCredentialsDTO data) {
		if (data == null || data.getUsername() == null || data.getUsername().isBlank() || data.getPassword() == null
				|| data.getPassword().isBlank()) {
			throw new ForbbidenException("Invalid client request!");
		}
	}
	
	private void validateTokenResponse(TokenDTO tokenResponse) {
		if (tokenResponse == null) {
			throw new ForbbidenException("Invalid client request!");
		}
	}

}
