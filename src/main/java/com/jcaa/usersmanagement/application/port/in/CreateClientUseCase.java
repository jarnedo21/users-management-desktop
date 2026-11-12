package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateClientCommand;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CreateClientUseCase {
  ClientModel execute(@NotNull @Valid CreateClientCommand command);
}
