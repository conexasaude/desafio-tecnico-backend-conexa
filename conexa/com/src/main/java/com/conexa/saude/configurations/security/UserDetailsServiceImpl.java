package com.conexa.saude.configurations.security;

import com.conexa.saude.constants.AuthoritiesEnum;
import com.conexa.saude.excepetions.UnauthorizedException;
import com.conexa.saude.model.entity.DoctorEntity;
import com.conexa.saude.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DoctorEntity doctor = doctorRepository.findByEmail(username)
                .orElseThrow(() -> new UnauthorizedException(String.format("Usuario %s não encontrado", username)));

        // por enquanto só tem a role de medico, pode adicionar controle de roles no
        // futuro
        return User.builder()
                .username(doctor.getEmail())
                .password(doctor.getSenha())
                .authorities(Arrays.asList(new SimpleGrantedAuthority(AuthoritiesEnum.DOCTOR.name())))
                .build();
    }
}
