package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.ClientActivityType;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import com.jcaa.usersmanagement.domain.valueobject.ClientName;
import com.jcaa.usersmanagement.domain.valueobject.ClientPhone;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.ClientPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.ClientEntity;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ClientPersistenceMapper {

  public ClientPersistenceDto fromModelToDto(final ClientModel client) {
    return new ClientPersistenceDto(
        client.getCode().value(),
        client.getName().value(),
        client.getAddress(),
        client.getPhone().value(),
        client.getContactPerson(),
        client.getActivityType().getDatabaseValue());
  }

  public ClientEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new ClientEntity(
        resultSet.getString("codigo"),
        resultSet.getString("nombre"),
        resultSet.getString("direccion"),
        resultSet.getString("telefono"),
        resultSet.getString("persona_contacto"),
        resultSet.getString("tipo_actividad"));
  }

  public ClientModel fromEntityToModel(final ClientEntity entity) {
    return new ClientModel(
        new ClientCode(entity.code()),
        new ClientName(entity.name()),
        entity.address(),
        new ClientPhone(entity.phone()),
        entity.contactPerson(),
        ClientActivityType.fromString(entity.activityType()));
  }

  public ClientModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<ClientModel> fromResultSetToModelList(final ResultSet resultSet)
      throws SQLException {
    final List<ClientModel> clients = new ArrayList<>();
    while (resultSet.next()) {
      clients.add(fromResultSetToModel(resultSet));
    }
    return clients;
  }
}
