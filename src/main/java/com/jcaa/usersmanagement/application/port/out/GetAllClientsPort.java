package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ClientModel;
import java.util.List;

public interface GetAllClientsPort {
  List<ClientModel> getAll();
}
