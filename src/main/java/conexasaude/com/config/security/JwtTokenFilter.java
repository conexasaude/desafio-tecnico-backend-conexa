package conexasaude.com.config.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import conexasaude.com.domain.repository.BlacklistedTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest,
                                    @NotNull HttpServletResponse httpServletResponse,
                                    @NotNull FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.getTokenFromAuthorizationHeader(httpServletRequest);

            if (Optional.ofNullable(token).isPresent() && !token.isEmpty() && !blacklistedTokenRepository.existsById(token)) {
                DecodedJWT decodedToken = jwtTokenProvider.decodeToken(token);
                Authentication auth = jwtTokenProvider.getAuthentication(decodedToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}