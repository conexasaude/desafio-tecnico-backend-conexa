package br.com.cleonildo.schedulingappoinment.exceptions.controller;


import br.com.cleonildo.schedulingappoinment.exceptions.NotFoundException;
import br.com.cleonildo.schedulingappoinment.exceptions.PasswordDoesNotMatch;
import br.com.cleonildo.schedulingappoinment.exceptions.response.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorExceptionHandler {
    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(NotFoundException notFoundException) {

        var err = ErrorResponse
                .builder()
                .status(NOT_FOUND.value())
                .statusErrorMessage(NOT_FOUND.getReasonPhrase())
                .message(notFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(err);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> integrityConstraint(
            SQLIntegrityConstraintViolationException constraintViolationException) {

        var err = ErrorResponse
                .builder()
                .status(BAD_REQUEST.value())
                .statusErrorMessage(BAD_REQUEST.getReasonPhrase())
                .message(constraintViolationException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> constraintErrosMessages(MethodArgumentNotValidException validException) {

        var errorMessages = this.errorMessagesJoin(validException);

        var err = ErrorResponse
                .builder()
                .status(BAD_REQUEST.value())
                .statusErrorMessage(BAD_REQUEST.getReasonPhrase())
                .message(errorMessages)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(err);
    }

    private String errorMessagesJoin(BindException exception){
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }


    @ExceptionHandler(PasswordDoesNotMatch.class)
    public ResponseEntity<ErrorResponse> constraintErrosMessages(PasswordDoesNotMatch notMatch) {

        var err = ErrorResponse
                .builder()
                .status(BAD_REQUEST.value())
                .statusErrorMessage(BAD_REQUEST.getReasonPhrase())
                .message(notMatch.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(err);
    }
}
