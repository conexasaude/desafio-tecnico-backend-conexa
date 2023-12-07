package com.felipe.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.felipe.exceptions.BadRequestException;
import com.felipe.exceptions.ForbbidenException;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.model.Doctor;
import com.felipe.model.User;
import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.model.dto.v1.PasswordUpdateDTO;
import com.felipe.model.dto.v1.security.AccountCredentialsDTO;
import com.felipe.model.dto.v1.security.CreateUserDoctorDTO;
import com.felipe.model.dto.v1.security.LogoutDTO;
import com.felipe.model.dto.v1.security.TokenDTO;
import com.felipe.repositories.UserRepository;
import com.felipe.security.jwt.JwtTokenProvider;
import com.felipe.util.MessageUtils;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private PasswordService passwordService;

	public ResponseEntity<TokenDTO> signin(AccountCredentialsDTO data) {
		try {
			checkParamsIsNotNull(data);

			var username = data.getEmail();
			var password = data.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			var user = userRepository.findByUserName(username)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND + ": Email " + username + " not found!"));

			var tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());
				jwtTokenProvider.allowRefreshToken(tokenResponse.getRefreshToken(), username);
			
			validateTokenResponse(tokenResponse);

			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException(MessageUtils.INVALID_EMAIL_PASSWORD);
		}
	}

	public ResponseEntity<TokenDTO> refreshToken(String username, String refreshToken, LogoutDTO dto) {
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
			throw new BadRequestException(MessageUtils.INVALID_EMAIL_PASSWORD);
		}

		DoctorDTO doctor = new DoctorDTO(dto.getEmail(), dto.getFullName(), dto.getCpf(), dto.getBirthDate(), 
				 dto.getPhone(), dto.getSpecialty());

		Doctor createdDoctor = doctorService.create(doctor);
		
        String passwordEncoded = passwordService.encodePassword(dto.getPassword());
	
		User user = new User(dto.getEmail(), passwordEncoded, true, true, true, true, createdDoctor);
		userRepository.save(user);
		logger.info("User created");

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<String> logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring("Bearer ".length());
        }
        jwtTokenProvider.revokeAllTokens(token);
        return ResponseEntity.ok("Logged out successfully");
	}

//	public void changePassword(String email, PasswordUpdateDTO passwordUpdateDTO) {
//		logger.info("Changing password");
//		try {
//			User entity = userRepository.findByUserName(email)
//					.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
//			PasswordEncoder passwordEncoder = getPasswordEncoder();
//			
//			if (passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), entity.getPassword())) {
//				if (passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmNewPassword())) {
//					entity.setPassword(passwordUpdateDTO.getNewPassword());
//					userRepository.save(entity);
//				} else {
//					throw new BadRequestException(MessageUtils.NEW_PASSWORD_MISMATCH);
//				}
//			}
//		} catch (Exception e) {
//			if (e.getMessage().contains("Detected a Non-hex character at 1 or 2 position")) {
//				throw new BadRequestException(MessageUtils.IVALID_PASSWORD);
//			}
//			throw new BadRequestException(e.getMessage());
//		}
//	}
	
	public void changePassword(String email, PasswordUpdateDTO passwordUpdateDTO) {
		logger.info("Changing password");
		try {
			User entity = userRepository.findByUserName(email)
					.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
			
			if (passwordService.matches(passwordUpdateDTO.getOldPassword(), entity.getPassword())) {
				if (passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmNewPassword())) {
					entity.setPassword(passwordService.encodePassword(passwordUpdateDTO.getNewPassword()));
					userRepository.save(entity);
				} else {
					throw new BadRequestException(MessageUtils.NEW_PASSWORD_MISMATCH);
				}
			}
		} catch (Exception e) {
			if (e.getMessage().contains("Detected a Non-hex character at 1 or 2 position")) {
				throw new BadRequestException(MessageUtils.IVALID_PASSWORD);
			}
			throw new BadRequestException(e.getMessage());
		}
	}

	private void checkParamsIsNotNull(String username, String refreshToken) {
		if (refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank()) {
			throw new ForbbidenException(MessageUtils.INVALID_CLIENT_REQUEST);
		}
	}

	private void checkParamsIsNotNull(AccountCredentialsDTO data) {
		if (data == null || data.getEmail() == null || data.getEmail().isBlank() || data.getPassword() == null
				|| data.getPassword().isBlank()) {
			throw new ForbbidenException(MessageUtils.INVALID_CLIENT_REQUEST);
		}
	}

	private void validateTokenResponse(TokenDTO tokenResponse) {
		if (tokenResponse == null) {
			throw new ForbbidenException(MessageUtils.INVALID_CLIENT_REQUEST);
		}
	}

}
