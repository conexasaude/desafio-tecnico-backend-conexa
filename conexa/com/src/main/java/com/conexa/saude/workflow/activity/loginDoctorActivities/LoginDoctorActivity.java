package com.conexa.saude.workflow.activity.loginDoctorActivities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.conexa.saude.configurations.security.TokenProvider;
import com.conexa.saude.excepetions.UnauthorizedException;
import com.conexa.saude.model.dto.LoginDTO;
import com.conexa.saude.workflow.activity.generics.BaseActivity;

@Service
public class LoginDoctorActivity implements BaseActivity<LoginDTO, String> {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public String doExecute(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return tokenProvider.getToken(authentication);

        } catch (Exception e) {
            throw new UnauthorizedException("Usuario não encontrado ou senha invalida");
        }

    }

}
