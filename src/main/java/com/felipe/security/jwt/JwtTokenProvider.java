package com.felipe.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.felipe.exceptions.InvalidJwtAuthenticationException;
import com.felipe.model.dto.v1.security.TokenDTO;
import com.felipe.service.UserService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {
	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";

	@Value("${security.jwt.token.expire-length:3600000}")
	private long validityInMilliseconds = 3600000; // 1h

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private Set<String> revokedTokens;

	Algorithm algorithm = null;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
		revokedTokens = redisTemplate.opsForSet().members("revokedTokens");
	}

	public TokenDTO createAccessToken(String username, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		var accessToken = getAccessToken(username, roles, now, validity);
		var refreshToken = getRefreshToken(username, roles, now);
		return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
	}

	public TokenDTO refreshToken(String refreshToken, String olderToken) {
		if (refreshToken.contains("Bearer ")) {
			refreshToken = refreshToken.substring("Bearer ".length());
		}
		DecodedJWT decodedJWT = decodedToken(refreshToken);

		String username = decodedJWT.getSubject();

		List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
		TokenDTO newToken = createAccessToken(username, roles);

		this.revokeToken(olderToken);
		this.revokeRefreshToken(username);
		this.allowRefreshToken(newToken.getRefreshToken(), decodedJWT.getSubject());
		return newToken;
	}

	public Authentication getAuthentication(String token) {
		DecodedJWT decodedJWT = decodedToken(token);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring("Bearer ".length());
		}
		return null;
	}

	public boolean validateToken(String token) throws Exception {
		DecodedJWT decodedJWT = decodedToken(token);
		if (redisTemplate.opsForSet().isMember("revokedTokens", token)) {
			throw new InvalidJwtAuthenticationException("Token has been revoked!");
		}
		if (decodedJWT.getExpiresAt().before(new Date())) {
			throw new InvalidJwtAuthenticationException("Token has expired!");
		}
		return true;
	}

	public void revokeAllTokens(String token) {

		DecodedJWT decodedJWT = decodedToken(token);
		this.revokeRefreshToken(decodedJWT.getSubject());
		this.revokeToken(token);
	}

	public void allowRefreshToken(String refreshToken, String subject) {
		String key = "allowedRefreshTokens:" + subject;
		redisTemplate.opsForValue().set(key, refreshToken, validityInMilliseconds * 3, TimeUnit.MILLISECONDS);
	}

	private void revokeToken(String token) {
		if (revokedTokens.contains(token)) {
			throw new InvalidJwtAuthenticationException("token already invalided!");
		}
		logger.info("token: " + token);
		redisTemplate.opsForSet().add("revokedTokens", token);
		redisTemplate.expire("revokedTokens", validityInMilliseconds, TimeUnit.MILLISECONDS);

		revokedTokens.add(token);
	}

	private void revokeRefreshToken(String subject) {
		String key = "allowedRefreshTokens:" + subject;
		redisTemplate.delete(key);
	}
	

	private DecodedJWT decodedToken(String token) {
		Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier verifier = JWT.require(alg).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		return decodedJWT;
	}

	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(validity).withSubject(username)
				.withIssuer(issuerUrl).sign(algorithm).strip();
	}

	private String getRefreshToken(String username, List<String> roles, Date now) {
		Date validityRefreshToken = new Date(now.getTime() + (validityInMilliseconds * 3));
		return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(validityRefreshToken)
				.withSubject(username).sign(algorithm).strip();
	}

}
