package com.jcaa.usersmanagement.domain.exception;

public final class ClientNotFoundException extends DomainException {

  private static final String MESSAGE_BY_CODE = "The client with code '%s' was not found.";

  private ClientNotFoundException(final String message) {
    super(message);
  }

  public static ClientNotFoundException becauseCodeWasNotFound(final String code) {
    return new ClientNotFoundException(String.format(MESSAGE_BY_CODE, code));
  }
}
