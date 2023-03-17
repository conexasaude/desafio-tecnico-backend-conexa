package com.conexa.desafio.security;

import com.conexa.desafio.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenerator tokenGenerator;

    @Autowired
    private TokenService tokenService;

    @Value("${conexa.desafio.headerString}")
    private String AUTH_HEADER;

    @Value("${conexa.desafio.tokenPrefix}")
    private String PREFIX;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = extraiTokenDaRequisicao(request);
        if(StringUtils.hasText(token) && tokenGenerator.validarToken(token) && tokenService.tokenJaExiste(token)){
            String email = tokenGenerator.buscaEmailDoToken(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String extraiTokenDaRequisicao(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);
        if( StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX)){
            return bearerToken.substring(PREFIX.length() + 1);
        }
        return null;
    }
}
