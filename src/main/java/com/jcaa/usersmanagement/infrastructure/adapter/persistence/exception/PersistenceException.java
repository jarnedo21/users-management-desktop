package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

public final class PersistenceException extends RuntimeException {

  private static final String MESSAGE_SAVE = "Failed to save user with ID: '%s'.";
  private static final String MESSAGE_UPDATE = "Failed to update user with ID: '%s'.";
  private static final String MESSAGE_FIND = "Failed to find user with ID: '%s'.";
  private static final String MESSAGE_EMAIL = "Failed to find user with email: '%s'.";
  private static final String MESSAGE_ALL = "Failed to retrieve all users.";
  private static final String MESSAGE_DELETE = "Failed to delete user with ID: '%s'.";
  private static final String MESSAGE_CONNECTION = "Could not establish database connection.";
  private static final String MESSAGE_CLIENT_SAVE = "Failed to save client with code: '%s'.";
  private static final String MESSAGE_CLIENT_UPDATE = "Failed to update client with code: '%s'.";
  private static final String MESSAGE_CLIENT_FIND = "Failed to find client with code: '%s'.";
  private static final String MESSAGE_CLIENT_ALL = "Failed to retrieve all clients.";
  private static final String MESSAGE_CLIENT_DELETE = "Failed to delete client with code: '%s'.";

  private PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static PersistenceException becauseSaveFailed(final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_SAVE, userId), cause);
  }

  public static PersistenceException becauseUpdateFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UPDATE, userId), cause);
  }

  public static PersistenceException becauseFindByIdFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND, userId), cause);
  }

  public static PersistenceException becauseFindByEmailFailed(
      final String email, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_EMAIL, email), cause);
  }

  public static PersistenceException becauseFindAllFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_ALL, cause);
  }

  public static PersistenceException becauseDeleteFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_DELETE, userId), cause);
  }

  public static PersistenceException becauseConnectionFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_CONNECTION, cause);
  }

  public static PersistenceException becauseClientSaveFailed(
      final String code, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_CLIENT_SAVE, code), cause);
  }

  public static PersistenceException becauseClientUpdateFailed(
      final String code, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_CLIENT_UPDATE, code), cause);
  }

  public static PersistenceException becauseClientFindByCodeFailed(
      final String code, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_CLIENT_FIND, code), cause);
  }

  public static PersistenceException becauseClientFindAllFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_CLIENT_ALL, cause);
  }

  public static PersistenceException becauseClientDeleteFailed(
      final String code, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_CLIENT_DELETE, code), cause);
  }
}
