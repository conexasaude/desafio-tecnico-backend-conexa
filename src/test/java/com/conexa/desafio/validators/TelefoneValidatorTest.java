package com.conexa.desafio.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelefoneValidatorTest {

  private TelefoneValidator telefoneValidator;

  @BeforeEach
  void beforeEach() {
    telefoneValidator = new TelefoneValidator();
  }

  @Test
  void deveValidarTelefonesFixosComDDDETraco() {
    boolean result = telefoneValidator.isValid("(74) 2535-7036", null);
    assertTrue(result);
    result = telefoneValidator.isValid("(074) 2535-7036", null);
    assertTrue(result);
  }

  @Test
  void deveValidarTelefoneFixoComDDDSemTraco() {
    boolean result = telefoneValidator.isValid("(74) 25357036", null);
    assertTrue(result);
    result = telefoneValidator.isValid("(074) 25357036", null);
    assertTrue(result);
  }

  @Test
  void deveValidarTelefoneCelularComDDDESemTracosOuEspacos() {
    boolean result = telefoneValidator.isValid("(74) 925357036", null);
    assertTrue(result);
    result = telefoneValidator.isValid("(074) 925357036", null);
    assertTrue(result);
  }

  @Test
  void deveValidarTelefoneCelularComDDDEComTracosESemEspacos() {
    boolean result = telefoneValidator.isValid("(74) 92535-7036", null);
    assertTrue(result);
    result = telefoneValidator.isValid("(074) 92535-7036", null);
    assertTrue(result);
  }

  @Test
  void deveValidarTelefoneCelularComDDDEComTracosEComEspacos() {
    boolean result = telefoneValidator.isValid("(74) 9 2535-7036", null);
    assertTrue(result);
    result = telefoneValidator.isValid("(074) 9 2535-7036", null);
    assertTrue(result);
  }

  @Test
  void deveValidarTelefoneCelularComDDDESemTracosEComEspacos() {
    boolean result = telefoneValidator.isValid("(74) 9 25357036", null);
    assertTrue(result);
    result = telefoneValidator.isValid("(074) 9 25357036", null);
    assertTrue(result);
  }

  @Test
  void deveInvalidarTelefoneFixoSemFormatoDeDDD(){
    boolean result = telefoneValidator.isValid("74 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("074 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("74 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("074 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("742535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("0742535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("7425357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("07425357036", null);
    assertFalse(result);
  }

  @Test
  void deveInvalidarTelefoneFixoComCodigoDoPais(){
    boolean result = telefoneValidator.isValid("+55 (74) 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 (074) 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 (74) 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 (074) 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 74 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 074 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 74 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 074 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 742535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 0742535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 7425357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 07425357036", null);
    assertFalse(result);
  }

  @Test
  void deveInvalidarTelefoneFixoComCodigoDoPaisESemEspacos(){
    boolean result = telefoneValidator.isValid("+55(74) 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55(074) 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55(74) 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55(074) 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+5574 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55074 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+5574 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55074 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55742535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+550742535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+557425357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+5507425357036", null);
    assertFalse(result);
  }

  @Test
  void deveInvalidarTelefoneCelularComCodigoDoPais(){
    boolean result = telefoneValidator.isValid("+55 (74) 92535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 (074) 92535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 (74) 925357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 (074) 9 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 (74) 9 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 (074) 925357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 74 925357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 (074) 9 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 74 9 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 074 925357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 74 92535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 074 9 25357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 74 9 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 074 92535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 7492535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 074 9 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 74 9 2535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 07492535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 74925357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55 074925357036", null);
    assertFalse(result);
  }

  @Test
  void deveInvalidarTelefoneCelularComCodigoDoPaisESemEspaco(){
    boolean result = telefoneValidator.isValid("+55(74) 92535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55(074) 92535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55(74) 925357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55(074) 925357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+5574 925357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55074 925357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+5574 92535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55074 92535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+557492535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+5507492535-7036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+5574925357036", null);
    assertFalse(result);
    result = telefoneValidator.isValid("+55074925357036", null);
    assertFalse(result);
  }

}
