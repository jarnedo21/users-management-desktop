package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidClientNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ClientNameTest {

  @Test
  @DisplayName("Should create ClientName with trimmed value")
  void shouldCreateClientNameWithTrimmedValue() {
    final ClientName name = new ClientName("  Moda Caribe S.A.S  ");
    assertEquals("Moda Caribe S.A.S", name.value());
    assertEquals("Moda Caribe S.A.S", name.toString());
  }

  @Test
  @DisplayName("Should throw NullPointerException when ClientName is null")
  void shouldThrowNullPointerExceptionWhenNull() {
    assertThrows(NullPointerException.class, () -> new ClientName(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t"})
  @DisplayName("Should throw InvalidClientNameException when ClientName is empty")
  void shouldThrowWhenEmpty(final String input) {
    assertThrows(InvalidClientNameException.class, () -> new ClientName(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"A", "AB", " AB "})
  @DisplayName("Should throw InvalidClientNameException when ClientName is too short")
  void shouldThrowWhenTooShort(final String input) {
    assertThrows(InvalidClientNameException.class, () -> new ClientName(input));
  }
}
