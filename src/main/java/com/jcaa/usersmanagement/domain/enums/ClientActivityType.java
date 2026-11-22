package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidClientActivityTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Tipo de actividad del cliente: empresa de moda o empresa de publicidad y cine.
 *
 * <p>{@code databaseValue} es el valor permitido por la restriccion CK_CLIENTE_TIPO de la tabla
 * CLIENTE ('MODA' o 'PUBLICIDAD_CINE').
 */
@Getter
@RequiredArgsConstructor
public enum ClientActivityType {
  FASHION("MODA"),
  ADVERTISING_FILM("PUBLICIDAD_CINE");

  private final String databaseValue;

  /** Acepta el nombre del enum o el valor de base de datos, sin distinguir mayusculas. */
  public static ClientActivityType fromString(final String value) {
    for (final ClientActivityType type : values()) {
      if (type.name().equalsIgnoreCase(value) || type.databaseValue.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw InvalidClientActivityTypeException.becauseValueIsInvalid(value);
  }
}
