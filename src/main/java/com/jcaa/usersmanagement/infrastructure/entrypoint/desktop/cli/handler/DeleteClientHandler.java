package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ClientNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ClientController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteClientHandler implements OperationHandler {

  private final ClientController clientController;
  private final ConsoleIO console;

  @Override
  public void handle() {
    final String code = console.readRequired("Client code to delete: ");
    try {
      clientController.deleteClient(code);
      console.println("  Client deleted successfully.");
    } catch (final ClientNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
