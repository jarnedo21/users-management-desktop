package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetClientByCodePort;
import com.jcaa.usersmanagement.application.port.out.UpdateClientPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateClientCommand;
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

@DisplayName("UpdateClientService")
@ExtendWith(MockitoExtension.class)
class UpdateClientServiceTest {

  @Mock private UpdateClientPort updateClientPort;
  @Mock private GetClientByCodePort getClientByCodePort;

  private UpdateClientService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new UpdateClientService(
              updateClientPort, getClientByCodePort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() actualiza el cliente cuando existe")
  void shouldUpdateClientWhenExists() {
    final ClientModel current = client("Moda Caribe", ClientActivityType.FASHION);
    final ClientModel updated = client("Cine Caribe", ClientActivityType.ADVERTISING_FILM);
    when(getClientByCodePort.getByCode(new ClientCode("CLI-001")))
        .thenReturn(Optional.of(current));
    when(updateClientPort.update(updated)).thenReturn(updated);

    final ClientModel result =
        service.execute(
            new UpdateClientCommand(
                "CLI-001",
                "Cine Caribe",
                "Calle 30",
                "3001234567",
                "Laura Perez",
                "ADVERTISING_FILM"));

    assertEquals(updated, result);
  }

  @Test
  @DisplayName("execute() lanza ClientNotFoundException cuando el cliente no existe")
  void shouldThrowWhenClientDoesNotExist() {
    when(getClientByCodePort.getByCode(any())).thenReturn(Optional.empty());

    assertThrows(
        ClientNotFoundException.class,
        () ->
            service.execute(
                new UpdateClientCommand(
                    "NOPE", "Cine Caribe", "Calle 30", "3001234567", "Laura", "FASHION")));
    verify(updateClientPort, never()).update(any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el command es invalido")
  void shouldThrowWhenCommandIsInvalid() {
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(new UpdateClientCommand("", "", "", "", "", "")));
    verifyNoInteractions(getClientByCodePort, updateClientPort);
  }

  private static ClientModel client(final String name, final ClientActivityType type) {
    return new ClientModel(
        new ClientCode("CLI-001"),
        new ClientName(name),
        "Calle 30",
        new ClientPhone("3001234567"),
        "Laura Perez",
        type);
  }
}
