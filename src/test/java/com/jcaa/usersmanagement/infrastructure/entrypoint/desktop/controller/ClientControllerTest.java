package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.in.CreateClientUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteClientUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllClientsUseCase;
import com.jcaa.usersmanagement.application.port.in.GetClientByCodeUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateClientUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateClientCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteClientCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateClientCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetClientByCodeQuery;
import com.jcaa.usersmanagement.domain.enums.ClientActivityType;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import com.jcaa.usersmanagement.domain.valueobject.ClientName;
import com.jcaa.usersmanagement.domain.valueobject.ClientPhone;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ClientResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateClientRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateClientRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ClientController")
@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

  @Mock private CreateClientUseCase createClientUseCase;
  @Mock private UpdateClientUseCase updateClientUseCase;
  @Mock private DeleteClientUseCase deleteClientUseCase;
  @Mock private GetClientByCodeUseCase getClientByCodeUseCase;
  @Mock private GetAllClientsUseCase getAllClientsUseCase;

  private ClientController controller;

  private static final ClientModel CLIENT =
      new ClientModel(
          new ClientCode("CLI-001"),
          new ClientName("Moda Caribe"),
          "Calle 30",
          new ClientPhone("3001234567"),
          "Laura Perez",
          ClientActivityType.FASHION);

  private static final ClientResponse RESPONSE =
      new ClientResponse(
          "CLI-001", "Moda Caribe", "Calle 30", "3001234567", "Laura Perez", "MODA");

  @BeforeEach
  void setUp() {
    controller =
        new ClientController(
            createClientUseCase,
            updateClientUseCase,
            deleteClientUseCase,
            getClientByCodeUseCase,
            getAllClientsUseCase);
  }

  @Test
  @DisplayName("listAllClients() mapea los modelos a respuestas")
  void shouldListAllClients() {
    when(getAllClientsUseCase.execute()).thenReturn(List.of(CLIENT));

    assertEquals(List.of(RESPONSE), controller.listAllClients());
  }

  @Test
  @DisplayName("findClientByCode() delega en el caso de uso con la query correcta")
  void shouldFindClientByCode() {
    when(getClientByCodeUseCase.execute(new GetClientByCodeQuery("CLI-001"))).thenReturn(CLIENT);

    assertEquals(RESPONSE, controller.findClientByCode("CLI-001"));
  }

  @Test
  @DisplayName("createClient() convierte el request en command y retorna la respuesta")
  void shouldCreateClient() {
    final CreateClientCommand command =
        new CreateClientCommand(
            "CLI-001", "Moda Caribe", "Calle 30", "3001234567", "Laura Perez", "FASHION");
    when(createClientUseCase.execute(command)).thenReturn(CLIENT);

    final ClientResponse result =
        controller.createClient(
            new CreateClientRequest(
                "CLI-001", "Moda Caribe", "Calle 30", "3001234567", "Laura Perez", "FASHION"));

    assertEquals(RESPONSE, result);
  }

  @Test
  @DisplayName("updateClient() convierte el request en command y retorna la respuesta")
  void shouldUpdateClient() {
    final UpdateClientCommand command =
        new UpdateClientCommand(
            "CLI-001", "Moda Caribe", "Calle 30", "3001234567", "Laura Perez", "FASHION");
    when(updateClientUseCase.execute(command)).thenReturn(CLIENT);

    final ClientResponse result =
        controller.updateClient(
            new UpdateClientRequest(
                "CLI-001", "Moda Caribe", "Calle 30", "3001234567", "Laura Perez", "FASHION"));

    assertEquals(RESPONSE, result);
  }

  @Test
  @DisplayName("deleteClient() delega en el caso de uso con el command correcto")
  void shouldDeleteClient() {
    controller.deleteClient("CLI-001");

    verify(deleteClientUseCase).execute(new DeleteClientCommand("CLI-001"));
  }
}
