package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.ClientModel;
import java.util.List;

public interface GetAllClientsUseCase {
  List<ClientModel> execute();
}
