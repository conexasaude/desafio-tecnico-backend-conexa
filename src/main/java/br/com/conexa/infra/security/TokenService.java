package br.com.conexa.infra.security;

import br.com.conexa.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public Set<String> invalidTokens = new HashSet<>();

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            Objects.requireNonNull(user, "User não pode ser vazio");

            return JWT.create()
            .withIssuer("conexa-saude")
            .withSubject(user.getUsername())
            .withExpiresAt(genExpirationDate())
            .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("conexa-saude")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    // Método auxiliar para extrair o token do cabeçalho Authorization
    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7).trim(); // Remove o prefixo "Bearer "
        }
        return null;
    }


    // Método para gerar um novo token

    public void invalidateToken(String token) {
        // Adicione o token à lista de tokens inválidos
        invalidTokens.add(token);
    }

    public boolean isTokenInvalid(String token) {
        // Verifique se o token está na lista de tokens inválidos
        return invalidTokens.contains(token);
    }
}
