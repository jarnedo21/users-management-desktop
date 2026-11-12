package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.ClientCode;

public interface DeleteClientPort {
  void delete(ClientCode code);
}
