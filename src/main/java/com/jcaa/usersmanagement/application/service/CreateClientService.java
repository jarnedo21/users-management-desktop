package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateClientUseCase;
import com.jcaa.usersmanagement.application.port.out.GetClientByCodePort;
import com.jcaa.usersmanagement.application.port.out.SaveClientPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateClientCommand;
import com.jcaa.usersmanagement.application.service.mapper.ClientApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ClientAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class CreateClientService implements CreateClientUseCase {

  private final SaveClientPort saveClientPort;
  private final GetClientByCodePort getClientByCodePort;
  private final Validator validator;

  @Override
  public ClientModel execute(final CreateClientCommand command) {
    validateCommand(command);

    final ClientCode code = new ClientCode(command.code());
    ensureCodeIsNotTaken(code);

    final ClientModel clientToSave = ClientApplicationMapper.fromCreateCommandToModel(command);
    return saveClientPort.save(clientToSave);
  }

  private void validateCommand(final CreateClientCommand command) {
    final Set<ConstraintViolation<CreateClientCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureCodeIsNotTaken(final ClientCode code) {
    getClientByCodePort
        .getByCode(code)
        .ifPresent(
            ignored -> {
              throw ClientAlreadyExistsException.becauseCodeAlreadyExists(code.value());
            });
  }
}
