package com.jcaa.usersmanagement.domain.exception;

public final class ClientAlreadyExistsException extends DomainException {

  private static final String MESSAGE_CODE_EXISTS = "A client with code '%s' already exists.";

  private ClientAlreadyExistsException(final String message) {
    super(message);
  }

  public static ClientAlreadyExistsException becauseCodeAlreadyExists(final String code) {
    return new ClientAlreadyExistsException(String.format(MESSAGE_CODE_EXISTS, code));
  }
}
