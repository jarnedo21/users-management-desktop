package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetAllClientsPort;
import com.jcaa.usersmanagement.domain.enums.ClientActivityType;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import com.jcaa.usersmanagement.domain.valueobject.ClientName;
import com.jcaa.usersmanagement.domain.valueobject.ClientPhone;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetAllClientsService")
@ExtendWith(MockitoExtension.class)
class GetAllClientsServiceTest {

  @Mock private GetAllClientsPort getAllClientsPort;

  private GetAllClientsService service;

  @BeforeEach
  void setUp() {
    service = new GetAllClientsService(getAllClientsPort);
  }

  @Test
  @DisplayName("execute() retorna todos los clientes del puerto")
  void shouldReturnAllClients() {
    final ClientModel client =
        new ClientModel(
            new ClientCode("CLI-001"),
            new ClientName("Cine Andino"),
            "Av. Pedro de Heredia",
            new ClientPhone("6056641234"),
            "Carlos Gomez",
            ClientActivityType.ADVERTISING_FILM);
    when(getAllClientsPort.getAll()).thenReturn(List.of(client));

    assertEquals(List.of(client), service.execute());
  }

  @Test
  @DisplayName("execute() retorna lista vacia cuando no hay clientes")
  void shouldReturnEmptyListWhenNoClients() {
    when(getAllClientsPort.getAll()).thenReturn(List.of());

    assertTrue(service.execute().isEmpty());
  }
}
