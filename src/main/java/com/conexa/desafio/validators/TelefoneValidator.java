package com.conexa.desafio.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class TelefoneValidator implements ConstraintValidator<Telefone, String> {

  private static final String TELEFONE_REGEX =
      "\\(\\d{2,3}\\)\\s((\\d{8,9})|(\\d\\s\\d{4}-\\d{4})|(\\d{5}-\\d{4})|(\\d\\s\\d{8})|(\\d{4}-\\d{4}))";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.compile(TELEFONE_REGEX).matcher(s).matches();
    }
}
