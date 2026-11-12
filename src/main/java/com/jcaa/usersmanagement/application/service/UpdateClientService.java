package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateClientUseCase;
import com.jcaa.usersmanagement.application.port.out.GetClientByCodePort;
import com.jcaa.usersmanagement.application.port.out.UpdateClientPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateClientCommand;
import com.jcaa.usersmanagement.application.service.mapper.ClientApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ClientNotFoundException;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class UpdateClientService implements UpdateClientUseCase {

  private final UpdateClientPort updateClientPort;
  private final GetClientByCodePort getClientByCodePort;
  private final Validator validator;

  @Override
  public ClientModel execute(final UpdateClientCommand command) {
    validateCommand(command);

    final ClientCode code = new ClientCode(command.code());
    ensureClientExists(code);

    final ClientModel clientToUpdate = ClientApplicationMapper.fromUpdateCommandToModel(command);
    return updateClientPort.update(clientToUpdate);
  }

  private void validateCommand(final UpdateClientCommand command) {
    final Set<ConstraintViolation<UpdateClientCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureClientExists(final ClientCode code) {
    getClientByCodePort
        .getByCode(code)
        .orElseThrow(() -> ClientNotFoundException.becauseCodeWasNotFound(code.value()));
  }
}
