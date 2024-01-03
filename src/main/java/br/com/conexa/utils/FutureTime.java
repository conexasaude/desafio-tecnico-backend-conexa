package br.com.conexa.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FutureTimeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureTime {

    String message() default "A Data deve ser no futuro";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
