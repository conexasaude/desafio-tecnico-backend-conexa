package br.com.conexa.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelefoneConstraintValidator implements ConstraintValidator<Telefone, String> {

    @Override
    public void initialize(Telefone arg0) {
    }

    @Override
    public boolean isValid(String telefone, ConstraintValidatorContext context) {
        String regex = "\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telefone);

        return matcher.matches();
    }
}
