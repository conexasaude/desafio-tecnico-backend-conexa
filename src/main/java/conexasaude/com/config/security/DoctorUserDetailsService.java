package conexasaude.com.config.security;

import conexasaude.com.domain.exceptions.LoginFailedException;
import conexasaude.com.domain.repository.DoctorUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorUserDetailsService implements UserDetailsService {

    private final DoctorUserRepository doctorUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return doctorUserRepository.findByEmailIgnoreCase(username)
                .map(DoctorUserDetails::new)
                .orElseThrow(LoginFailedException::new);
    }
}
