package com.felipe.model.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = FutureDateTimeValidator.class)
public @interface FutureDateTime {

    String message() default "A data e hora devem estar no futuro";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
