package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ClientModel;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import java.util.Optional;

public interface GetClientByCodePort {
  Optional<ClientModel> getByCode(ClientCode code);
}
