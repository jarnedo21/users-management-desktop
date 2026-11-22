package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidClientPhoneException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ClientPhoneTest {

  @ParameterizedTest
  @ValueSource(strings = {"3001234567", "+57 300 123 4567", "(605) 664-1234", " 6056641234 "})
  @DisplayName("Should create ClientPhone when format is valid")
  void shouldCreateClientPhoneWhenFormatIsValid(final String input) {
    final ClientPhone phone = new ClientPhone(input);
    assertEquals(input.trim(), phone.value());
    assertEquals(input.trim(), phone.toString());
  }

  @Test
  @DisplayName("Should throw NullPointerException when ClientPhone is null")
  void shouldThrowNullPointerExceptionWhenNull() {
    assertThrows(NullPointerException.class, () -> new ClientPhone(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   "})
  @DisplayName("Should throw InvalidClientPhoneException when ClientPhone is empty")
  void shouldThrowWhenEmpty(final String input) {
    assertThrows(InvalidClientPhoneException.class, () -> new ClientPhone(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"12345", "300-ABC-4567", "123456789012345678901", "57+3001234567"})
  @DisplayName("Should throw InvalidClientPhoneException when format is invalid")
  void shouldThrowWhenFormatIsInvalid(final String input) {
    assertThrows(InvalidClientPhoneException.class, () -> new ClientPhone(input));
  }
}
