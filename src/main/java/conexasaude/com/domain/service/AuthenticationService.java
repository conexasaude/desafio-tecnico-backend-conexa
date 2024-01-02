package conexasaude.com.domain.service;


import conexasaude.com.domain.dto.doctor.DoctorUserLoginResponseDto;
import conexasaude.com.domain.entity.BlacklistedToken;
import conexasaude.com.domain.entity.DoctorUser;
import conexasaude.com.domain.exceptions.EmailNotAvailableException;
import conexasaude.com.domain.exceptions.LoginFailedException;
import conexasaude.com.domain.repository.BlacklistedTokenRepository;
import conexasaude.com.domain.repository.DoctorUserRepository;
import conexasaude.com.config.security.JwtTokenProvider;
import conexasaude.com.config.security.DoctorUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final DoctorUserRepository doctorUserRepository;
    private final AuthenticationManager authenticationManager;
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    public void save(DoctorUser doctorUser) {
        if (doctorUserRepository.existsByEmailIgnoreCase(doctorUser.getEmail()))
            throw new EmailNotAvailableException();

        doctorUser.setPassword(passwordEncoder.encode(doctorUser.getPassword()));
        doctorUserRepository.save(doctorUser);
    }


    public DoctorUserLoginResponseDto login(String email, String password) {
        var doctor = doctorUserRepository.findByEmailIgnoreCase(email).orElseThrow(LoginFailedException::new);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            throw new LoginFailedException();
        }

        return DoctorUserLoginResponseDto.builder()
                .token(jwtTokenProvider.createToken(new DoctorUserDetails(doctor)))
                .build();
    }

    public void logoff(HttpServletRequest req) {
        blacklistedTokenRepository.save(new BlacklistedToken(jwtTokenProvider.getTokenFromAuthorizationHeader(req)));
    }
}
