package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ClientResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ClientController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ClientResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListClientsHandler implements OperationHandler {

  private final ClientController clientController;
  private final ClientResponsePrinter printer;

  @Override
  public void handle() {
    final List<ClientResponse> clients = clientController.listAllClients();
    printer.printList(clients);
  }
}
