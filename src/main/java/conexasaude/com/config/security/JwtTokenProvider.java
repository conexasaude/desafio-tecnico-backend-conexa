package conexasaude.com.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-in-hours}")
    private long validityInHours;

    private final DoctorUserDetailsService doctorUserDetailsService;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @SneakyThrows
    public String createToken(DoctorUserDetails userDetails) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + (validityInHours * 60 * 60 * 1000));

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(validity)
                .withIssuedAt(now)
                .withArrayClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().toArray(new String[] {}))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String getTokenFromAuthorizationHeader(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        if (Optional.ofNullable(bearerToken).isPresent())
            return bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : bearerToken;

        return null;
    }

    public Authentication getAuthentication(DecodedJWT decodedToken) {
        try {
            var userDetails = doctorUserDetailsService.loadUserByUsername(decodedToken.getSubject());
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } catch (UsernameNotFoundException e) {
            return null;
        }
    }

    public DecodedJWT decodeToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
        } catch (JWTVerificationException exception) {
            // Invalid token
            return null;
        }
    }
}
