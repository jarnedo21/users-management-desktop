package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllClientsUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllClientsPort;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class GetAllClientsService implements GetAllClientsUseCase {

  private final GetAllClientsPort getAllClientsPort;

  @Override
  public List<ClientModel> execute() {
    return getAllClientsPort.getAll();
  }
}
