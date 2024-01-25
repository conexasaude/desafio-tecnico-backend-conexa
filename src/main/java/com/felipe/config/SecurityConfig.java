package com.felipe.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;

import com.felipe.security.jwt.JwtConfigurer;
import com.felipe.security.jwt.JwtTokenProvider;

@EnableWebSecurity
@Configuration
//@Profile({"dev", "test"})
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;

	public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(
								"/api/v1/signup",
								"/api/v1/login",
								"/api/v1/refresh/**",
//								"/api/v1/**",
								"/swagger-ui/**",
								"/v3/api-docs/**").permitAll()
						.requestMatchers("/api/**").authenticated().requestMatchers("/users").denyAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.apply(new JwtConfigurer(jwtTokenProvider));
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder("", 8, 185000,
				SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		encoders.put("pbkdf2", pbkdf2PasswordEncoder);
		DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2PasswordEncoder);
		return delegatingPasswordEncoder;
	}

	@Bean
	AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
