package conexasaude.com.domain.exceptions;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        super("Email ou senha incorreto");
    }
}
