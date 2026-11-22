package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.DeleteClientPort;
import com.jcaa.usersmanagement.application.port.out.GetClientByCodePort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteClientCommand;
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

@DisplayName("DeleteClientService")
@ExtendWith(MockitoExtension.class)
class DeleteClientServiceTest {

  @Mock private DeleteClientPort deleteClientPort;
  @Mock private GetClientByCodePort getClientByCodePort;

  private DeleteClientService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new DeleteClientService(
              deleteClientPort, getClientByCodePort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() elimina el cliente cuando existe")
  void shouldDeleteClientWhenExists() {
    final ClientCode code = new ClientCode("CLI-001");
    final ClientModel client =
        new ClientModel(
            code,
            new ClientName("Moda Caribe"),
            "Calle 30",
            new ClientPhone("3001234567"),
            "Laura Perez",
            ClientActivityType.FASHION);
    when(getClientByCodePort.getByCode(code)).thenReturn(Optional.of(client));

    service.execute(new DeleteClientCommand("CLI-001"));

    verify(deleteClientPort).delete(code);
  }

  @Test
  @DisplayName("execute() lanza ClientNotFoundException cuando el cliente no existe")
  void shouldThrowWhenClientDoesNotExist() {
    when(getClientByCodePort.getByCode(any())).thenReturn(Optional.empty());

    assertThrows(
        ClientNotFoundException.class, () -> service.execute(new DeleteClientCommand("NOPE")));
    verify(deleteClientPort, never()).delete(any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el codigo esta vacio")
  void shouldThrowWhenCommandIsInvalid() {
    assertThrows(
        ConstraintViolationException.class, () -> service.execute(new DeleteClientCommand("")));
    verifyNoInteractions(getClientByCodePort, deleteClientPort);
  }
}
