package conexasaude.com.config.security;

import conexasaude.com.domain.entity.DoctorUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class DoctorUserDetails implements UserDetails {
    private final DoctorUser doctorUser;

    public DoctorUserDetails(DoctorUser doctor) {
        this.doctorUser = doctor;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(DoctorUser.ROLE_NAME);
    }

    @Override
    public String getPassword() {
        return doctorUser.getPassword();
    }

    @Override
    public String getUsername() {
        return doctorUser.getEmail();
    }

    public DoctorUser getDoctorUser() {
        return doctorUser;
    }
}
