package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.CreateClientUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteClientUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllClientsUseCase;
import com.jcaa.usersmanagement.application.port.in.GetClientByCodeUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateClientUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ClientResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateClientRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateClientRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.ClientDesktopMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class ClientController {

  private final CreateClientUseCase createClientUseCase;
  private final UpdateClientUseCase updateClientUseCase;
  private final DeleteClientUseCase deleteClientUseCase;
  private final GetClientByCodeUseCase getClientByCodeUseCase;
  private final GetAllClientsUseCase getAllClientsUseCase;

  public List<ClientResponse> listAllClients() {
    final var clients = getAllClientsUseCase.execute();
    return ClientDesktopMapper.toResponseList(clients);
  }

  public ClientResponse findClientByCode(final String code) {
    final var query = ClientDesktopMapper.toGetByCodeQuery(code);
    final var client = getClientByCodeUseCase.execute(query);
    return ClientDesktopMapper.toResponse(client);
  }

  public ClientResponse createClient(final CreateClientRequest request) {
    final var command = ClientDesktopMapper.toCreateCommand(request);
    final var client = createClientUseCase.execute(command);
    return ClientDesktopMapper.toResponse(client);
  }

  public ClientResponse updateClient(final UpdateClientRequest request) {
    final var command = ClientDesktopMapper.toUpdateCommand(request);
    final var client = updateClientUseCase.execute(command);
    return ClientDesktopMapper.toResponse(client);
  }

  public void deleteClient(final String code) {
    final var command = ClientDesktopMapper.toDeleteCommand(code);
    deleteClientUseCase.execute(command);
  }
}
