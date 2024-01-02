package conexasaude.com.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "token_blacklist")
public class BlacklistedToken {

    @Id
    private String token;

    public BlacklistedToken() {}

    public BlacklistedToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}