package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteClientPort;
import com.jcaa.usersmanagement.application.port.out.GetAllClientsPort;
import com.jcaa.usersmanagement.application.port.out.GetClientByCodePort;
import com.jcaa.usersmanagement.application.port.out.SaveClientPort;
import com.jcaa.usersmanagement.application.port.out.UpdateClientPort;
import com.jcaa.usersmanagement.domain.exception.ClientNotFoundException;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.ClientPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.ClientPersistenceMapper;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Adaptador de salida (SQL Server) para la entidad Cliente.
 *
 * <p>Opera sobre la tabla {@code cliente} del modelo entidad-relacion usando JDBC puro (sin ORM).
 */
@RequiredArgsConstructor
public final class ClientRepositorySqlServer
    implements SaveClientPort,
        UpdateClientPort,
        GetClientByCodePort,
        GetAllClientsPort,
        DeleteClientPort {

  private static final String SQL_INSERT =
      "INSERT INTO cliente "
      + "(codigo, nombre, direccion, telefono, persona_contacto, tipo_actividad) "
      + "VALUES (?, ?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE =
      "UPDATE cliente SET nombre = ?, direccion = ?, telefono = ?, persona_contacto = ?, "
      + "tipo_actividad = ? "
      + "WHERE codigo = ?";

  private static final String SQL_SELECT_BY_CODE =
      "SELECT TOP 1 codigo, nombre, direccion, telefono, persona_contacto, tipo_actividad "
      + "FROM cliente "
      + "WHERE codigo = ?";

  private static final String SQL_SELECT_ALL =
      "SELECT codigo, nombre, direccion, telefono, persona_contacto, tipo_actividad "
      + "FROM cliente "
      + "ORDER BY nombre ASC";

  private static final String SQL_DELETE =
      "DELETE FROM cliente "
      + "WHERE codigo = ?";

  private final Connection connection;

  @Override
  public ClientModel save(final ClientModel client) {
    final ClientPersistenceDto dto = ClientPersistenceMapper.fromModelToDto(client);
    executeSave(dto);
    return findByCodeOrFail(client.getCode());
  }

  @Override
  public ClientModel update(final ClientModel client) {
    final ClientPersistenceDto dto = ClientPersistenceMapper.fromModelToDto(client);
    executeUpdate(dto);
    return findByCodeOrFail(client.getCode());
  }

  @Override
  public Optional<ClientModel> getByCode(final ClientCode code) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_CODE)) {
      statement.setString(1, code.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(ClientPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseClientFindByCodeFailed(code.value(), exception);
    }
  }

  @Override
  public List<ClientModel> getAll() {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return ClientPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseClientFindAllFailed(exception);
    }
  }

  @Override
  public void delete(final ClientCode code) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setString(1, code.value());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseClientDeleteFailed(code.value(), exception);
    }
  }

  private void executeSave(final ClientPersistenceDto dto) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, dto.code());
      statement.setString(2, dto.name());
      statement.setString(3, dto.address());
      statement.setString(4, dto.phone());
      statement.setString(5, dto.contactPerson());
      statement.setString(6, dto.activityType());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseClientSaveFailed(dto.code(), exception);
    }
  }

  private void executeUpdate(final ClientPersistenceDto dto) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, dto.name());
      statement.setString(2, dto.address());
      statement.setString(3, dto.phone());
      statement.setString(4, dto.contactPerson());
      statement.setString(5, dto.activityType());
      statement.setString(6, dto.code());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseClientUpdateFailed(dto.code(), exception);
    }
  }

  private ClientModel findByCodeOrFail(final ClientCode code) {
    return getByCode(code)
        .orElseThrow(() -> ClientNotFoundException.becauseCodeWasNotFound(code.value()));
  }
}
