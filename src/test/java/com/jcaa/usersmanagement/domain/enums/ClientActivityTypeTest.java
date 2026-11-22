package com.jcaa.usersmanagement.domain.enums;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidClientActivityTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ClientActivityTypeTest {

  @ParameterizedTest
  @CsvSource({
    "FASHION, FASHION",
    "fashion, FASHION",
    "ADVERTISING_FILM, ADVERTISING_FILM",
    "advertising_film, ADVERTISING_FILM",
    "MODA, FASHION",
    "moda, FASHION",
    "PUBLICIDAD_CINE, ADVERTISING_FILM",
    "publicidad_cine, ADVERTISING_FILM"
  })
  @DisplayName("fromString() resolves enum names and database values ignoring case")
  void shouldResolveIgnoringCase(final String input, final ClientActivityType expected) {
    assertEquals(expected, ClientActivityType.fromString(input));
  }

  @ParameterizedTest
  @CsvSource({"FASHION, MODA", "ADVERTISING_FILM, PUBLICIDAD_CINE"})
  @DisplayName("getDatabaseValue() matches the CK_CLIENTE_TIPO constraint values")
  void shouldExposeDatabaseValue(final ClientActivityType type, final String expected) {
    assertEquals(expected, type.getDatabaseValue());
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"", "CINE", "MODA_Y_CINE", "PUBLICIDAD"})
  @DisplayName("fromString() throws InvalidClientActivityTypeException for unknown values")
  void shouldThrowForUnknownValues(final String input) {
    assertThrows(
        InvalidClientActivityTypeException.class, () -> ClientActivityType.fromString(input));
  }
}
