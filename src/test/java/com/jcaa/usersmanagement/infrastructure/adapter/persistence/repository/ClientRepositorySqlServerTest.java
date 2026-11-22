package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.domain.enums.ClientActivityType;
import com.jcaa.usersmanagement.domain.exception.ClientNotFoundException;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import com.jcaa.usersmanagement.domain.valueobject.ClientName;
import com.jcaa.usersmanagement.domain.valueobject.ClientPhone;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests para ClientRepositorySqlServer. La conexion JDBC se simula con Mockito, por lo que no se
 * requiere una base de datos real.
 */
@DisplayName("ClientRepositorySqlServer")
@ExtendWith(MockitoExtension.class)
class ClientRepositorySqlServerTest {

  private static final String CODE = "CLI-001";

  @Mock private Connection connection;
  @Mock private PreparedStatement statement;
  @Mock private ResultSet resultSet;

  private ClientRepositorySqlServer repository;

  @BeforeEach
  void setUp() {
    repository = new ClientRepositorySqlServer(connection);
  }

  @Test
  @DisplayName("save() ejecuta INSERT y retorna el cliente leido por codigo")
  void shouldSaveAndReturnPersistedClient() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    stubRow();

    final ClientModel result = repository.save(client());

    assertEquals(client(), result);
    verify(statement).executeUpdate();
    verify(statement).setString(6, "MODA");
  }

  @Test
  @DisplayName("save() lanza PersistenceException cuando el INSERT falla")
  void shouldThrowWhenInsertFails() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenThrow(new SQLException("duplicate"));

    assertThrows(PersistenceException.class, () -> repository.save(client()));
  }

  @Test
  @DisplayName("save() lanza ClientNotFoundException si no se encuentra tras el INSERT")
  void shouldThrowWhenNotFoundAfterInsert() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(false);

    assertThrows(ClientNotFoundException.class, () -> repository.save(client()));
  }

  @Test
  @DisplayName("update() ejecuta UPDATE y retorna el cliente actualizado")
  void shouldUpdateAndReturnClient() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    stubRow();

    assertEquals(client(), repository.update(client()));
    verify(statement).setString(6, CODE);
  }

  @Test
  @DisplayName("update() lanza PersistenceException cuando el UPDATE falla")
  void shouldThrowWhenUpdateFails() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenThrow(new SQLException("error"));

    assertThrows(PersistenceException.class, () -> repository.update(client()));
  }

  @Test
  @DisplayName("getByCode() retorna Optional vacio cuando no hay filas")
  void shouldReturnEmptyWhenNoRows() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(false);

    assertEquals(Optional.empty(), repository.getByCode(new ClientCode(CODE)));
  }

  @Test
  @DisplayName("getByCode() lanza PersistenceException cuando la consulta falla")
  void shouldThrowWhenSelectFails() throws SQLException {
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("error"));

    assertThrows(PersistenceException.class, () -> repository.getByCode(new ClientCode(CODE)));
  }

  @Test
  @DisplayName("getAll() retorna la lista de clientes")
  void shouldReturnAllClients() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true, false);
    stubRow();

    assertEquals(List.of(client()), repository.getAll());
  }

  @Test
  @DisplayName("getAll() lanza PersistenceException cuando la consulta falla")
  void shouldThrowWhenGetAllFails() throws SQLException {
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("error"));

    assertThrows(PersistenceException.class, () -> repository.getAll());
  }

  @Test
  @DisplayName("delete() ejecuta DELETE con el codigo")
  void shouldDeleteByCode() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);

    repository.delete(new ClientCode(CODE));

    verify(statement).setString(1, CODE);
    verify(statement).executeUpdate();
  }

  @Test
  @DisplayName("delete() lanza PersistenceException cuando el DELETE falla")
  void shouldThrowWhenDeleteFails() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenThrow(new SQLException("fk violation"));

    assertThrows(PersistenceException.class, () -> repository.delete(new ClientCode(CODE)));
  }

  private void stubRow() throws SQLException {
    when(resultSet.getString("codigo")).thenReturn(CODE);
    when(resultSet.getString("nombre")).thenReturn("Moda Caribe");
    when(resultSet.getString("direccion")).thenReturn("Calle 30");
    when(resultSet.getString("telefono")).thenReturn("3001234567");
    when(resultSet.getString("persona_contacto")).thenReturn("Laura Perez");
    when(resultSet.getString("tipo_actividad")).thenReturn("MODA");
  }

  private static ClientModel client() {
    return new ClientModel(
        new ClientCode(CODE),
        new ClientName("Moda Caribe"),
        "Calle 30",
        new ClientPhone("3001234567"),
        "Laura Perez",
        ClientActivityType.FASHION);
  }
}
