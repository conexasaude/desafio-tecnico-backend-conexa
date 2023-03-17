package com.conexa.desafio.security;

import com.conexa.desafio.models.UsuarioEntity;
import com.conexa.desafio.services.TokenService;
import com.conexa.desafio.services.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenerator tokenGenerator;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @Value("${conexa.desafio.headerString}")
    private String AUTH_HEADER;

    @Value("${conexa.desafio.tokenPrefix}")
    private String PREFIX;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = extraiTokenDaRequisicao(request);
        if(StringUtils.hasText(token) && tokenGenerator.validarToken(token) && tokenService.tokenJaExiste(token)){
            String email = tokenGenerator.buscaEmailDoToken(token);
            UsuarioEntity usuarioEntity = usuarioService.buscarPorEmail(email);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usuarioEntity, null);
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
