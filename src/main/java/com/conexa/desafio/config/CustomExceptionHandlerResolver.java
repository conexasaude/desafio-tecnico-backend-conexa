package com.conexa.desafio.config;

import com.conexa.desafio.payload.BaseResponse;
import com.conexa.desafio.payload.ErrosResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ControllerAdvice
public class CustomExceptionHandlerResolver {

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrosResponse errosResponse = constroiResponse(ex, extrairErros(ex));
        return new ResponseEntity<>(BaseResponse.buildBaseResponse(HttpStatus.BAD_REQUEST, errosResponse), HttpStatus.BAD_REQUEST);
    }

    private ErrosResponse constroiResponse(MethodArgumentNotValidException exception, List<ErrosResponse.Erro> erros){
        return new ErrosResponse(exception.getObjectName(), erros);
    }

    private List<ErrosResponse.Erro> extrairErros(MethodArgumentNotValidException exception){
        return exception.getBindingResult().getFieldErrors().stream()
                .map(erro -> new ErrosResponse.Erro(erro.getField(), erro.getDefaultMessage()))
                .collect(Collectors.toList());
    }

}