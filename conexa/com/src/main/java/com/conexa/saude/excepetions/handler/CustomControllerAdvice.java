package com.conexa.saude.excepetions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.conexa.saude.excepetions.BadRequestException;
import com.conexa.saude.excepetions.NotFoundException;
import com.conexa.saude.excepetions.UnauthorizedException;
import com.conexa.saude.excepetions.dto.ExceptionDTO;

@ControllerAdvice
class CustomControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDTO> handleBadRequestExceptions(Exception e) {
        var badRequestException = (BadRequestException) e; 
        return handle(e, HttpStatus.BAD_REQUEST, badRequestException.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDTO> handleUnauthorizedException(Exception e) {
        var unauthorizedException = (UnauthorizedException) e;
        return handle(e, HttpStatus.UNAUTHORIZED, unauthorizedException.getMessage());
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleNotFoundExceptions(Exception e) {
        var notFoundException = (NotFoundException) e;
        return handle(e, HttpStatus.NOT_FOUND, notFoundException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleExceptions(Exception e) {
        var messageError = "Tivemos um problema, estamos trabalhando para solucionar. Tente novamente mais tarde";
        return handle(e, HttpStatus.INTERNAL_SERVER_ERROR, messageError);
    }

    private ResponseEntity<ExceptionDTO> handle(Exception e, HttpStatus status, String message) {

        for(int i = 0; i < e.getStackTrace().length; i++) {
            System.out.println(e.getStackTrace()[i].toString());
        }
     

        return new ResponseEntity<>(ExceptionDTO.builder()
                .status(status.value())
                .message(message)
                .build(),
                status);
    }

}