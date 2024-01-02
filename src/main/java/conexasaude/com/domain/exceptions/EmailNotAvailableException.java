package conexasaude.com.domain.exceptions;

public class EmailNotAvailableException extends RuntimeException {

    public EmailNotAvailableException() {
        super("Email não disponível");
    }
}
