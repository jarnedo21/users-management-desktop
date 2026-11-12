package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ClientNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ClientResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ClientController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ClientResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindClientByCodeHandler implements OperationHandler {

  private final ClientController clientController;
  private final ConsoleIO console;
  private final ClientResponsePrinter printer;

  @Override
  public void handle() {
    final String code = console.readRequired("Client code: ");
    try {
      final ClientResponse client = clientController.findClientByCode(code);
      printer.print(client);
    } catch (final ClientNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
