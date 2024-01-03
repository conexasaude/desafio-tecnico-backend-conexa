package br.com.conexa.validationDTO;

import br.com.conexa.utils.PasswordConstraintValidator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordConstraintValidatorTest {

    @Test
    public void passwordValidation() {
        PasswordConstraintValidator pass = new PasswordConstraintValidator();
        //Validos
        String password1 = "ConexaS@ude123";
        String password2 = "30Conexas@ude1234567890123456";

        //Invalidos
        String password3 = "123";
        String password4 = "conexa";
        String password5 = "conexasaude123";
        String password6 = "+30Conexas@ude12345678901234567";


        // Telefones válidos
        assertTrue(pass.isValid(password1, null));
        assertTrue(pass.isValid(password2, null));


        // Telefones inválidos
        assertFalse(pass.isValid(password3, null));
        assertFalse(pass.isValid(password4, null));
        assertFalse(pass.isValid(password5, null));
        assertFalse(pass.isValid(password6, null));

    }
}
