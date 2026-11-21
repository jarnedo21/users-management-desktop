package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ClientAlreadyExistsException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ClientResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ClientController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ClientResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateClientRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateClientHandler implements OperationHandler {

  private final ClientController clientController;
  private final ConsoleIO console;
  private final ClientResponsePrinter printer;

  @Override
  public void handle() {
    final String code          = console.readRequired("Code                                   : ");
    final String name          = console.readRequired("Name                                   : ");
    final String address       = console.readRequired("Address                                : ");
    final String phone         = console.readRequired("Phone                                  : ");
    final String contactPerson = console.readRequired("Contact person                         : ");
    final String activityType  = console.readRequired("Activity type (MODA / PUBLICIDAD_CINE)  : ");

    try {
      final ClientResponse created =
          clientController.createClient(
              new CreateClientRequest(code, name, address, phone, contactPerson, activityType));
      console.println("\n  Client created successfully.");
      printer.print(created);
    } catch (final ClientAlreadyExistsException exception) {
      console.println("  Error: " + exception.getMessage());
    }
  }
}
