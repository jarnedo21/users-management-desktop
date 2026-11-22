package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetClientByCodeUseCase;
import com.jcaa.usersmanagement.application.port.out.GetClientByCodePort;
import com.jcaa.usersmanagement.application.service.dto.query.GetClientByCodeQuery;
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
public final class GetClientByCodeService implements GetClientByCodeUseCase {

  private final GetClientByCodePort getClientByCodePort;
  private final Validator validator;

  @Override
  public ClientModel execute(final GetClientByCodeQuery query) {
    validateQuery(query);

    final ClientCode code = ClientApplicationMapper.fromGetClientByCodeQueryToCode(query);
    return getClientByCodePort
        .getByCode(code)
        .orElseThrow(() -> ClientNotFoundException.becauseCodeWasNotFound(code.value()));
  }

  private void validateQuery(final GetClientByCodeQuery query) {
    final Set<ConstraintViolation<GetClientByCodeQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
