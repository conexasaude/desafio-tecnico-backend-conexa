package conexasaude.com.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailNotAvailableException.class)
    public ResponseEntity<Object> emailNotAvailableException(EmailNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInfoException.class)
    public ResponseEntity<Object> invalidInfoException(InvalidInfoException e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingInfoException.class)
    public ResponseEntity<Object> missingInfoException(MissingInfoException e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<Object> loginFailedException(LoginFailedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }
}
