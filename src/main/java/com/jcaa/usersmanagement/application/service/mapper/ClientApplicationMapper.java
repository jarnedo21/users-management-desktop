package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateClientCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetClientByCodeQuery;
import com.jcaa.usersmanagement.domain.enums.ClientActivityType;
import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import com.jcaa.usersmanagement.domain.valueobject.ClientName;
import com.jcaa.usersmanagement.domain.valueobject.ClientPhone;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientApplicationMapper {

  public ClientModel fromCreateCommandToModel(final CreateClientCommand command) {
    return ClientModel.create(
        new ClientCode(command.code()),
        new ClientName(command.name()),
        command.address().trim(),
        new ClientPhone(command.phone()),
        command.contactPerson().trim(),
        ClientActivityType.fromString(command.activityType()));
  }

  public ClientCode fromGetClientByCodeQueryToCode(final GetClientByCodeQuery query) {
    return new ClientCode(query.code());
  }
}
