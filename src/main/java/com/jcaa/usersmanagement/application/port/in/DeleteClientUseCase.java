package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.DeleteClientCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeleteClientUseCase {
  void execute(@NotNull @Valid DeleteClientCommand command);
}
