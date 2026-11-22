package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidClientCodeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ClientCodeTest {

  @ParameterizedTest
  @ValueSource(strings = {" CLI-001 ", "CLI-001", "\tCLI-001\t"})
  @DisplayName("Should create ClientCode with trimmed value")
  void shouldCreateClientCodeWithTrimmedValue(final String input) {
    final ClientCode code = new ClientCode(input);
    assertEquals("CLI-001", code.value());
    assertEquals("CLI-001", code.toString());
  }

  @Test
  @DisplayName("Should throw NullPointerException when ClientCode is null")
  void shouldThrowNullPointerExceptionWhenNull() {
    assertThrows(NullPointerException.class, () -> new ClientCode(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n"})
  @DisplayName("Should throw InvalidClientCodeException when ClientCode is empty")
  void shouldThrowWhenEmpty(final String input) {
    assertThrows(InvalidClientCodeException.class, () -> new ClientCode(input));
  }

  @Test
  @DisplayName("Should throw InvalidClientCodeException when ClientCode exceeds 10 characters")
  void shouldThrowWhenTooLong() {
    final String tooLong = "X".repeat(11);
    assertThrows(InvalidClientCodeException.class, () -> new ClientCode(tooLong));
  }
}
