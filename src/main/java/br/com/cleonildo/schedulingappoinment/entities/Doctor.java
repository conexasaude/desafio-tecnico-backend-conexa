package br.com.cleonildo.schedulingappoinment.entities;

import br.com.cleonildo.schedulingappoinment.enums.Role;
import br.com.cleonildo.schedulingappoinment.enums.Specialty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "doctor")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Doctor implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Specialty specialty;
    @Column(unique = true, nullable = false)
    private String cpf;
    private LocalDate birthdate;
    private String phone;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Doctor(String email, String password, Specialty specialty, String cpf, LocalDate birthdate, String phone) {
        this.email = email;
        this.password = password;
        this.specialty = specialty;
        this.cpf = cpf;
        this.birthdate = birthdate;
        this.phone = phone;
        this.role = Role.ADMIN;
    }
    public Doctor(Long id, String email, String password, Specialty specialty, String cpf, LocalDate birthdate, String phone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.specialty = specialty;
        this.cpf = cpf;
        this.birthdate = birthdate;
        this.phone = phone;
        this.role = Role.ADMIN;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getUsername() {
        return this.email;
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
}