package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ClientNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ClientResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ClientController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ClientResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateClientRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateClientHandler implements OperationHandler {

  private final ClientController clientController;
  private final ConsoleIO console;
  private final ClientResponsePrinter printer;

  @Override
  public void handle() {
    final String code          = console.readRequired("Client code                            : ");
    final String name          = console.readRequired("New name                               : ");
    final String address       = console.readRequired("New address                            : ");
    final String phone         = console.readRequired("New phone                              : ");
    final String contactPerson = console.readRequired("New contact person                     : ");
    final String activityType  = console.readRequired("Activity type (FASHION / ADVERTISING_FILM): ");

    try {
      final ClientResponse updated =
          clientController.updateClient(
              new UpdateClientRequest(code, name, address, phone, contactPerson, activityType));
      console.println("\n  Client updated successfully.");
      printer.print(updated);
    } catch (final ClientNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
