package br.com.conexa.services;

import br.com.conexa.domain.user.AuthenticationDTO;
import br.com.conexa.domain.user.RegisterDTO;
import br.com.conexa.domain.user.User;
import br.com.conexa.infra.security.TokenService;
import br.com.conexa.repositories.UserRepository;
import br.com.conexa.utils.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public Optional<UserDetails> signup(@Valid RegisterDTO data) {

        final User newUser = new User(data.email(),
                                Util.encryptedPassword(data),
                                data.especialidade(),
                                data.cpf(),
                                data.dataNascimento(),
                                data.telefone());

        return Optional.of(this.userRepository.save(newUser));
    }

    public String loginTokenGenerate(@Valid AuthenticationDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        return this.tokenService.generateToken((User) auth.getPrincipal());
    }

    public ResponseEntity<String> logoff(String authorizationHeader) {
        String token = this.tokenService.extractToken(authorizationHeader);

        if (token != null) {
            this.tokenService.invalidateToken(token);
            return ResponseEntity.ok("Logout bem-sucedido");
        } else {
            return ResponseEntity.badRequest().body("Token ausente ou inválido");
        }
    }
}
