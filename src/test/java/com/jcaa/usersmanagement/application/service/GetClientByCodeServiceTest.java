package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetClientByCodePort;
import com.jcaa.usersmanagement.application.service.dto.query.GetClientByCodeQuery;
import com.jcaa.usersmanagement.domain.enums.ClientActivityType;
import com.jcaa.usersmanagement.domain.exception.ClientNotFoundException;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import com.jcaa.usersmanagement.domain.valueobject.ClientName;
import com.jcaa.usersmanagement.domain.valueobject.ClientPhone;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetClientByCodeService")
@ExtendWith(MockitoExtension.class)
class GetClientByCodeServiceTest {

  @Mock private GetClientByCodePort getClientByCodePort;

  private GetClientByCodeService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new GetClientByCodeService(getClientByCodePort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() retorna el cliente cuando existe")
  void shouldReturnClientWhenFound() {
    final ClientModel client =
        new ClientModel(
            new ClientCode("CLI-001"),
            new ClientName("Moda Caribe"),
            "Calle 30",
            new ClientPhone("3001234567"),
            "Laura Perez",
            ClientActivityType.FASHION);
    when(getClientByCodePort.getByCode(new ClientCode("CLI-001"))).thenReturn(Optional.of(client));

    assertEquals(client, service.execute(new GetClientByCodeQuery(" CLI-001 ")));
  }

  @Test
  @DisplayName("execute() lanza ClientNotFoundException cuando no existe")
  void shouldThrowWhenNotFound() {
    when(getClientByCodePort.getByCode(new ClientCode("NOPE"))).thenReturn(Optional.empty());

    assertThrows(
        ClientNotFoundException.class, () -> service.execute(new GetClientByCodeQuery("NOPE")));
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el codigo esta vacio")
  void shouldThrowWhenQueryIsInvalid() {
    assertThrows(
        ConstraintViolationException.class, () -> service.execute(new GetClientByCodeQuery(" ")));
    verifyNoInteractions(getClientByCodePort);
  }
}
