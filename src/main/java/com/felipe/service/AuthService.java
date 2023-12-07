package com.felipe.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.stereotype.Service;

import com.felipe.exceptions.BadRequestException;
import com.felipe.exceptions.ForbbidenException;
import com.felipe.model.Doctor;
import com.felipe.model.User;
import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.model.dto.v1.security.AccountCredentialsDTO;
import com.felipe.model.dto.v1.security.CreateUserDoctorDTO;
import com.felipe.model.dto.v1.security.LogoutDTO;
import com.felipe.model.dto.v1.security.TokenDTO;
import com.felipe.repositories.UserRepository;
import com.felipe.security.jwt.JwtTokenProvider;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DoctorService doctorService;

	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsDTO data) {
		try {
			checkParamsIsNotNull(data);

			var username = data.getEmail();
			var password = data.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			var user = userRepository.findByUserName(username);

			var tokenResponse = new TokenDTO();

			if (user != null) {
				tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());
				jwtTokenProvider.allowRefreshToken(tokenResponse.getRefreshToken(), username);
			} else {
				throw new UsernameNotFoundException("Email " + username + " not found!");
			}
			validateTokenResponse(tokenResponse);

			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid email/password supplied!");
		}
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken, LogoutDTO dto) {
		checkParamsIsNotNull(username, refreshToken);

		var user = userRepository.findByUserName(username);

		var tokenResponse = new TokenDTO();
		if (user != null) {
			tokenResponse = jwtTokenProvider.refreshToken(refreshToken, dto.getAcessToken());
		} else {
			throw new UsernameNotFoundException("Email " + username + " not found!");
		}
		validateTokenResponse(tokenResponse);

		return ResponseEntity.ok(tokenResponse);
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public ResponseEntity signup(CreateUserDoctorDTO dto) throws Exception {
		if (!dto.getConfirmPassword().equals(dto.getPassword())) {
			throw new BadRequestException("Password is not equals");
		}

		DoctorDTO doctor = new DoctorDTO(dto.getEmail(), dto.getFullName(), dto.getCpf(), dto.getBirthDate(), 
				 dto.getPhone(), dto.getSpecialty());

		Doctor createdDoctor = doctorService.create(doctor);

		Map<String, PasswordEncoder> encoders = new HashMap<>();
		Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder("", 8, 185000,
				SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		encoders.put("pbkdf2", pbkdf2PasswordEncoder);
		DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2PasswordEncoder);

		String passwordEncoded = delegatingPasswordEncoder.encode(dto.getPassword());
		
		User user = new User(dto.getEmail(), passwordEncoded, true, true, true, true, createdDoctor);
		userRepository.save(user);
		
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<String> logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring("Bearer ".length());
        }
        jwtTokenProvider.revokeAllTokens(token);
        return ResponseEntity.ok("Logged out successfully");
	}
	
	private void checkParamsIsNotNull(String username, String refreshToken) {
		if (refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank()) {
			throw new ForbbidenException("Invalid client request!");
		}
	}

	private void checkParamsIsNotNull(AccountCredentialsDTO data) {
		if (data == null || data.getEmail() == null || data.getEmail().isBlank() || data.getPassword() == null
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
