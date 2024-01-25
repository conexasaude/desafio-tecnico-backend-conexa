package com.felipe.model.validation;

import java.time.LocalDateTime;

import com.felipe.util.DateUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FutureDateTimeValidator implements ConstraintValidator<FutureDateTime, String> {

    @Override
    public void initialize(FutureDateTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Pode ser nulo, deixe a validação para a anotação @NotBlank
        }

        try {
            LocalDateTime dateTime = DateUtil.convertStringToLocalDateTime(value); // Assumindo que o formato está correto
            return dateTime.isAfter(LocalDateTime.now());
        } catch (Exception e) {
        	throw e;
//            return false; // Se houver um problema ao converter, considere inválido
        }
    }
}
