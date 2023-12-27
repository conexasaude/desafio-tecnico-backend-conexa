package br.com.cleonildo.schedulingappoinment.serivce;

import br.com.cleonildo.schedulingappoinment.dto.AuthenticationRequest;
import br.com.cleonildo.schedulingappoinment.dto.LoginResponse;
import br.com.cleonildo.schedulingappoinment.entities.Doctor;
import br.com.cleonildo.schedulingappoinment.repository.DoctorRepository;
import br.com.cleonildo.schedulingappoinment.security.TokenSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenSecurityService service;
    private final DoctorRepository repository;

    public LoginResponse loginService(AuthenticationRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var userDetails = (UserDetails) auth.getPrincipal();

        var doctor = (Doctor) this.repository
                .findByEmail(userDetails.getUsername())
                .orElseThrow();

        return new LoginResponse(this.service.generateToken(doctor));
    }
}
