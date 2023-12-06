package com.felipe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

//		Map<String, PasswordEncoder> encoders = new HashMap<>();
//		Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder("", 8, 185000,
//				SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
//		encoders.put("pbkdf2", pbkdf2PasswordEncoder);
//		DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
//		delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2PasswordEncoder);
//
//		String result1 = delegatingPasswordEncoder.encode("admin123");
//		String result2 = delegatingPasswordEncoder.encode("admin234");
//
//		System.out.println("My hash1 " + result1);
//		System.out.println("My hash2 " + result2);

	}

}
