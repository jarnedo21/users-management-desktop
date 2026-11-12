package com.jcaa.usersmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.ClientActivityType;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import com.jcaa.usersmanagement.domain.valueobject.ClientName;
import com.jcaa.usersmanagement.domain.valueobject.ClientPhone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClientModelTest {

  @Test
  @DisplayName("create() builds a client with all its attributes")
  void shouldCreateClientWithAllAttributes() {
    final ClientModel client =
        ClientModel.create(
            new ClientCode("CLI-001"),
            new ClientName("Moda Caribe"),
            "Calle 30 #10-20, Cartagena",
            new ClientPhone("3001234567"),
            "Laura Perez",
            ClientActivityType.FASHION);

    assertEquals("CLI-001", client.getCode().value());
    assertEquals("Moda Caribe", client.getName().value());
    assertEquals("Calle 30 #10-20, Cartagena", client.getAddress());
    assertEquals("3001234567", client.getPhone().value());
    assertEquals("Laura Perez", client.getContactPerson());
    assertEquals(ClientActivityType.FASHION, client.getActivityType());
  }

  @Test
  @DisplayName("isFashionCompany() is true only for FASHION clients")
  void shouldDetectFashionCompany() {
    final ClientModel fashion = buildClient(ClientActivityType.FASHION);
    final ClientModel film = buildClient(ClientActivityType.ADVERTISING_FILM);

    assertTrue(fashion.isFashionCompany());
    assertFalse(film.isFashionCompany());
  }

  @Test
  @DisplayName("Two clients with the same values are equal (value semantics)")
  void shouldBeEqualWhenValuesMatch() {
    assertEquals(
        buildClient(ClientActivityType.FASHION), buildClient(ClientActivityType.FASHION));
  }

  private static ClientModel buildClient(final ClientActivityType type) {
    return new ClientModel(
        new ClientCode("CLI-002"),
        new ClientName("Cine Andino"),
        "Av. Pedro de Heredia",
        new ClientPhone("6056641234"),
        "Carlos Gomez",
        type);
  }
}
