package br.com.conexa.validationDTO;

import br.com.conexa.utils.TelefoneConstraintValidator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TelefoneConstraintValidatorTest {

    /*@Test
    public void testTelefoneValidation() {
        // Crie uma instância da sua classe a ser validada
        //Validos
        final String telefone1 = "(21) 3232-6565";
        final String telefone2 = "(21)3232-6565";
        final String telefone3 = "(21) 93232-6565";
        final String telefone4 = "(21)99913-4567";
        final String telefone5 = "(21)29913-4567";

        //Invalidos
        final String telefone6 = "(21)913-4567";
        final String telefone7 = "(21)9913-567";
        //suaInstancia.setTelefone("(21) 3232-6565");
        // Valide a instância usando o validador
        //Set<ConstraintViolation<TelefoneConstraintValidator>> violations = ;
        // Verifique se há violações de validação
        assertEquals(0, validatorFactory.validate(telefone1).size(), "O telefone deve ser válido");
        assertEquals(0, validatorFactory.validate(telefone2).size(), "O telefone deve ser válido");
        assertEquals(0, validatorFactory.validate(telefone3).size(), "O telefone deve ser válido");
        assertEquals(0, validatorFactory.validate(telefone4).size(), "O telefone deve ser válido");
        assertEquals(0, validatorFactory.validate(telefone5).size(), "O telefone deve ser válido");



        // Modifique o telefone para um valor inválido
        assertEquals(1, validatorFactory.validate(telefone6).size(), "O telefone deve ser inválido");
        assertEquals(1, validatorFactory.validate(telefone7).size(), "O telefone deve ser inválido");
    }

     */

    @Test
    public void telefoneValidation() {
        TelefoneConstraintValidator telefoneValidator = new TelefoneConstraintValidator();

        // Telefones válidos
        assertTrue(telefoneValidator.isValid("(21) 3232-6565", null));
        assertTrue(telefoneValidator.isValid("(21)3232-6565", null));
        assertTrue(telefoneValidator.isValid("(21) 93232-6565", null));
        assertTrue(telefoneValidator.isValid("(21)99913-4567", null));
        assertTrue(telefoneValidator.isValid("(21)29913-4567", null));

        // Telefones inválidos
        assertFalse(telefoneValidator.isValid("(21)913-4567", null));
        assertFalse(telefoneValidator.isValid("(21)9913-567", null));
    }
}
