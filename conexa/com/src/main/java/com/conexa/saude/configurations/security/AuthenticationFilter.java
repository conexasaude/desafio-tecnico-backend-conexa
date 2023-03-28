package com.conexa.saude.configurations.security;

import com.conexa.saude.workflow.activity.loginDoctorActivities.VerifyBannedTokensActivity;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

import static com.conexa.saude.constants.ServiceConstants.*;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private VerifyBannedTokensActivity verifyBannedTokensActivity;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, ServletException, IOException {

        String token = getJwtfromRequest(request);
        boolean tokenBanned = verifyBannedTokensActivity.doExecute(token);

        if (!tokenBanned && Objects.nonNull(token)) {
            var webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);

            authenticateAndLogin(token, webAuthenticationDetails);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtfromRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);

        if (!StringUtils.isNullOrEmpty(token) && token.startsWith(BEARER)) {
            return token.substring(BEARER.length());
        }

        return null;
    }

    private Void authenticateAndLogin(String token, WebAuthenticationDetails webAuthenticationDetails) {

        if (jwtTokenProvider.validate(token)) {
            String username = jwtTokenProvider.getUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(webAuthenticationDetails);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        return null;

    }

}