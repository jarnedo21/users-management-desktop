package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteClientUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteClientPort;
import com.jcaa.usersmanagement.application.port.out.GetClientByCodePort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteClientCommand;
import com.jcaa.usersmanagement.application.service.mapper.ClientApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ClientNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class DeleteClientService implements DeleteClientUseCase {

  private final DeleteClientPort deleteClientPort;
  private final GetClientByCodePort getClientByCodePort;
  private final Validator validator;

  @Override
  public void execute(final DeleteClientCommand command) {
    validateCommand(command);

    final ClientCode code = ClientApplicationMapper.fromDeleteCommandToCode(command);
    ensureClientExists(code);
    deleteClientPort.delete(code);
  }

  private void validateCommand(final DeleteClientCommand command) {
    final Set<ConstraintViolation<DeleteClientCommand>> violations = validator.validate(command);
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
