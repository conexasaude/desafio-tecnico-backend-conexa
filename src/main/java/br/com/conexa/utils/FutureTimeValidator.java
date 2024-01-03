package br.com.conexa.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class FutureTimeValidator implements ConstraintValidator<FutureTime, LocalDateTime> {

    @Override
    public void initialize(FutureTime arg0) {
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        return value.isAfter(now);
    }
}
