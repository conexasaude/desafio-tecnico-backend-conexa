package br.com.cleonildo.schedulingappoinment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    ADMIN("Admin"), USER("User");

    private final String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
