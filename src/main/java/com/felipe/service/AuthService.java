package com.felipe.service;

import java.net.URI;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.felipe.exceptions.BadRequestException;
import com.felipe.exceptions.ForbbidenException;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.exceptions.UnauthorizedException;
import com.felipe.mapper.DoctorMapper;
import com.felipe.model.Doctor;
import com.felipe.model.User;
import com.felipe.model.dto.v1.CreateUserDoctorDTO;
import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.model.dto.v1.PasswordUpdateDTO;
import com.felipe.model.dto.v1.security.AccessTokenDTO;
import com.felipe.model.dto.v1.security.AccountCredentialsDTO;
import com.felipe.model.dto.v1.security.TokenDTO;
import com.felipe.repositories.DoctorRepository;
import com.felipe.repositories.UserRepository;
import com.felipe.security.jwt.JwtTokenProvider;
import com.felipe.util.MessageUtils;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
	private Logger logger = Logger.getLogger(AuthService.class.getName());

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private DoctorMapper doctorMapper;


	@Autowired
	private PasswordService passwordService;

	public ResponseEntity<AccessTokenDTO> signin(AccountCredentialsDTO data) {
		try {
			var username = data.getEmail();
			var password = data.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			var user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(
					MessageUtils.NO_RECORDS_FOUND + ": Email " + username + " not found!"));

			var tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());
			jwtTokenProvider.allowRefreshToken(tokenResponse.getRefreshToken(), username);

			validateTokenResponse(tokenResponse);
			HttpHeaders headers = getHeaderRefreshToken(tokenResponse);

			return ResponseEntity.ok().headers(headers).body(new AccessTokenDTO(tokenResponse.getAcessToken()));
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new UnauthorizedException(MessageUtils.INVALID_EMAIL_PASSWORD);
		}
	}

	public ResponseEntity<AccessTokenDTO> refreshToken(String username, String refreshToken, AccessTokenDTO dto) {
		checkParamsIsNotNull(username, refreshToken);

		var user = userRepository.findByUsername(username);

		var tokenResponse = new TokenDTO();
		if (user != null) {
			tokenResponse = jwtTokenProvider.refreshToken(refreshToken, dto.getAccessToken());
		} else {
			throw new UsernameNotFoundException("Email " + username + " not found!");
		}
		validateTokenResponse(tokenResponse);

		HttpHeaders headers = getHeaderRefreshToken(tokenResponse);

		return ResponseEntity.ok().headers(headers).body(new AccessTokenDTO(tokenResponse.getAcessToken()));
	}

	@Transactional
	public ResponseEntity<DoctorDTO> signup(CreateUserDoctorDTO dto) throws Exception {
		
		DoctorDTO doctor = new DoctorDTO(dto.getEmail(), dto.getFullName(), dto.getCpf(), dto.getBirthDate(),
				dto.getPhone(), dto.getSpecialty());

		Doctor createdDoctor = doctorMapper.toEntity(doctorService.create(doctor));

		String passwordEncoded = passwordService.encodePassword(dto.getPassword());

		User user = new User(dto.getEmail(), passwordEncoded, true, true, true, true, createdDoctor);

		User createdUser = userService.create(user);
		logger.info("User created");

		createdDoctor.setUser(createdUser);
		doctorRepository.save(createdDoctor);

		return ResponseEntity.created(new URI("/api/users/" + createdUser.getId())).body(doctorMapper.toDto(createdDoctor));
	}

	public ResponseEntity<String> logout(String token) {
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring("Bearer ".length());
		}
		jwtTokenProvider.revokeAllTokens(token);
		return ResponseEntity.ok("Logged out successfully");
	}

	public void changePassword(String email, PasswordUpdateDTO passwordUpdateDTO) {
		logger.info("Changing password");
		try {
			User entity = userRepository.findByUsername(email)
					.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));

			if (passwordService.matches(passwordUpdateDTO.getOldPassword(), entity.getPassword())) {
				entity.setPassword(passwordService.encodePassword(passwordUpdateDTO.getNewPassword()));
				userRepository.save(entity);
			}
		} catch (Exception e) {
			if (e.getMessage().contains("Detected a Non-hex character at 1 or 2 position")) {
				throw new BadRequestException(MessageUtils.IVALID_PASSWORD);
			}
			throw new BadRequestException(e.getMessage());
		}
	}

	private HttpHeaders getHeaderRefreshToken(TokenDTO tokenResponse) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Refresh-Token", tokenResponse.getRefreshToken());
		return headers;
	}

	private void checkParamsIsNotNull(String username, String refreshToken) {
		if (refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank()) {
			throw new ForbbidenException(MessageUtils.INVALID_CLIENT_REQUEST);
		}
	}

	private void validateTokenResponse(TokenDTO tokenResponse) {
		if (tokenResponse == null) {
			throw new ForbbidenException(MessageUtils.INVALID_CLIENT_REQUEST);
		}
	}

}
