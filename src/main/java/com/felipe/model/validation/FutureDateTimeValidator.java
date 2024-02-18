package com.felipe.model.validation;

import java.time.LocalDateTime;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FutureDateTimeValidator implements ConstraintValidator<FutureDateTime, LocalDateTime> {

    @Override
    public void initialize(FutureDateTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Pode ser nulo, deixe a validação para a anotação @NotBlank
        }

        try {
            return value.isAfter(LocalDateTime.now());
        } catch (Exception e) {
        	throw e;
//            return false; // Se houver um problema ao converter, considere inválido
        }
    }
}
