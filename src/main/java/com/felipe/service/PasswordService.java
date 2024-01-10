package com.felipe.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PasswordService {
	 private final PasswordEncoder passwordEncoder;

	    public PasswordService(PasswordEncoder passwordEncoder) {
	        this.passwordEncoder = passwordEncoder;
	    }

	    public String encodePassword(String plainPassword) {
	        return passwordEncoder.encode(plainPassword);
	    }

	    public boolean matches(String rawPassword, String encodedPassword) {
	        return passwordEncoder.matches(rawPassword, encodedPassword);
	    }

	    public PasswordEncoder getPasswordEncoder() {
	        return passwordEncoder;
	    }

	    public static PasswordService createPbkdf2PasswordEncoder() {
	        Map<String, PasswordEncoder> encoders = new HashMap<>();
	        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder("", 8, 185000,
	                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
	        encoders.put("pbkdf2", pbkdf2PasswordEncoder);
	        return new PasswordService(new DelegatingPasswordEncoder("pbkdf2", encoders));
	    }
}
