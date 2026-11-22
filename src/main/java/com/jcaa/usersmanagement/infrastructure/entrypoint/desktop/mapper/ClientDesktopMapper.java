package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateClientCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteClientCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateClientCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetClientByCodeQuery;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ClientResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateClientRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateClientRequest;

import java.util.List;

public final class ClientDesktopMapper {

  private ClientDesktopMapper() {}

  public static CreateClientCommand toCreateCommand(final CreateClientRequest request) {
    return new CreateClientCommand(
        request.code(),
        request.name(),
        request.address(),
        request.phone(),
        request.contactPerson(),
        request.activityType());
  }

  public static UpdateClientCommand toUpdateCommand(final UpdateClientRequest request) {
    return new UpdateClientCommand(
        request.code(),
        request.name(),
        request.address(),
        request.phone(),
        request.contactPerson(),
        request.activityType());
  }

  public static DeleteClientCommand toDeleteCommand(final String code) {
    return new DeleteClientCommand(code);
  }

  public static GetClientByCodeQuery toGetByCodeQuery(final String code) {
    return new GetClientByCodeQuery(code);
  }

  public static ClientResponse toResponse(final ClientModel client) {
    return new ClientResponse(
        client.getCode().value(),
        client.getName().value(),
        client.getAddress(),
        client.getPhone().value(),
        client.getContactPerson(),
        client.getActivityType().name());
  }

  public static List<ClientResponse> toResponseList(final List<ClientModel> clients) {
    return clients.stream().map(ClientDesktopMapper::toResponse).toList();
  }
}
