package br.com.cleonildo.schedulingappoinment.security;

import br.com.cleonildo.schedulingappoinment.entities.Doctor;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenSecurityService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Doctor doctor) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT
                    .create()
                    .withIssuer("auth")
                    .withSubject(doctor.getUsername())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT
                    .require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Error while verification token", exception);
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
