package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetClientByCodeQuery;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetClientByCodeUseCase {
  ClientModel execute(@NotNull @Valid GetClientByCodeQuery query);
}
