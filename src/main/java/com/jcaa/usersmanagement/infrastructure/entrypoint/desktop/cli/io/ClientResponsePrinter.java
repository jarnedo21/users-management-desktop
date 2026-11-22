package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ClientResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ClientResponsePrinter {

  private static final String SEPARATOR = "-".repeat(52);
  private static final String ROW_FORMAT = "  %-14s : %s%n";

  private final ConsoleIO console;

  public void print(final ClientResponse response) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "Code",           response.code());
    console.printf(ROW_FORMAT, "Name",           response.name());
    console.printf(ROW_FORMAT, "Address",        response.address());
    console.printf(ROW_FORMAT, "Phone",          response.phone());
    console.printf(ROW_FORMAT, "Contact person", response.contactPerson());
    console.printf(ROW_FORMAT, "Activity type",  response.activityType());
    console.println(SEPARATOR);
  }

  public void printList(final List<ClientResponse> clients) {
    if (clients.isEmpty()) {
      console.println("  No clients found.");
      return;
    }
    console.printf("%n  Total: %d client(s)%n", clients.size());
    clients.forEach(this::print);
  }
}
