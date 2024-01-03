package br.com.conexa.validationDTO;

import br.com.conexa.utils.FutureTimeValidator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FutureTimeConstraintValidatorTest {

    @Test
    public void testTelefoneValidation1() {

        FutureTimeValidator time = new FutureTimeValidator();

        LocalDateTime timePresente = LocalDateTime.now();
        LocalDateTime timePassado = timePresente.minusSeconds(1); // Subtrai 1 segundo do tempo presente
        LocalDateTime timeFuturo = timePresente.plusSeconds(1); // Adiciona 1 segundo ao tempo presente

        // Data valida
        assertTrue(time.isValid(timeFuturo, null));

        //Datas invalidas
        assertFalse(time.isValid(timePresente, null));
        assertFalse(time.isValid(timePassado, null));
        assertFalse(time.isValid(null, null));
    }
}
