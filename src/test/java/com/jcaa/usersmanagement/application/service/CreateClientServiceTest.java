package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetClientByCodePort;
import com.jcaa.usersmanagement.application.port.out.SaveClientPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateClientCommand;
import com.jcaa.usersmanagement.domain.enums.ClientActivityType;
import com.jcaa.usersmanagement.domain.exception.ClientAlreadyExistsException;
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

/**
 * Tests para CreateClientService.
 *
 * <p>Cubre: validacion del command, codigo duplicado y flujo feliz (save).
 */
@DisplayName("CreateClientService")
@ExtendWith(MockitoExtension.class)
class CreateClientServiceTest {

  @Mock private SaveClientPort saveClientPort;
  @Mock private GetClientByCodePort getClientByCodePort;

  private CreateClientService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new CreateClientService(
              saveClientPort, getClientByCodePort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() guarda el cliente cuando el codigo no existe")
  void shouldSaveClientWhenCodeIsNew() {
    // Arrange
    final CreateClientCommand command = validCommand();
    final ClientModel saved = expectedClient();
    when(getClientByCodePort.getByCode(any())).thenReturn(Optional.empty());
    when(saveClientPort.save(any())).thenReturn(saved);

    // Act
    final ClientModel result = service.execute(command);

    // Assert
    assertEquals(saved, result);
    verify(saveClientPort).save(saved);
  }

  @Test
  @DisplayName("execute() lanza ClientAlreadyExistsException cuando el codigo ya existe")
  void shouldThrowWhenCodeAlreadyExists() {
    // Arrange
    when(getClientByCodePort.getByCode(new ClientCode("CLI-001")))
        .thenReturn(Optional.of(expectedClient()));

    // Act & Assert
    assertThrows(ClientAlreadyExistsException.class, () -> service.execute(validCommand()));
    verify(saveClientPort, never()).save(any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el command es invalido")
  void shouldThrowWhenCommandIsInvalid() {
    // Arrange
    final CreateClientCommand invalid = new CreateClientCommand("", "AB", "", "", "", "");

    // Act & Assert
    assertThrows(ConstraintViolationException.class, () -> service.execute(invalid));
    verifyNoInteractions(getClientByCodePort, saveClientPort);
  }

  private static CreateClientCommand validCommand() {
    return new CreateClientCommand(
        "CLI-001", "Moda Caribe", "Calle 30 #10-20", "3001234567", "Laura Perez", "fashion");
  }

  private static ClientModel expectedClient() {
    return new ClientModel(
        new ClientCode("CLI-001"),
        new ClientName("Moda Caribe"),
        "Calle 30 #10-20",
        new ClientPhone("3001234567"),
        "Laura Perez",
        ClientActivityType.FASHION);
  }
}
