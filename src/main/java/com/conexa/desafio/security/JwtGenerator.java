package com.conexa.desafio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {

  @Value("${conexa.desafio.expirationTime}")
  private long JWT_EXPIRATION_INTERVAL;

  @Value("${conexa.desafio.secret}")
  private String SECRET;

  public String generateToken(Authentication authentication) {
    String email = authentication.getName();
    Date currentDate = new Date();
    Date expirationDate = new Date(currentDate.getTime() + JWT_EXPIRATION_INTERVAL);
    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(currentDate)
        .setExpiration(expirationDate)
        .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS512)
        .compact();
  }

  public String buscaEmailDoToken(String token) {
    Claims claims =
        Jwts.parserBuilder()
            .setSigningKey(SECRET.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody();
    return claims.getSubject();
  }

  public boolean validarToken(String token){
    try {
      Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes())).build().parseClaimsJws(token);
      return true;
    } catch (Exception e){
      return false;
    }
  }
}
