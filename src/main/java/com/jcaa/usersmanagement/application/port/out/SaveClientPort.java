package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ClientModel;

public interface SaveClientPort {
  ClientModel save(ClientModel client);
}
