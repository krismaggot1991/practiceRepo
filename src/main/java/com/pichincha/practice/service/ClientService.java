package com.pichincha.practice.service;

import com.pichincha.practice.domain.dto.client.request.ClientRequest;
import com.pichincha.practice.domain.jpa.Client;
import java.util.List;

public interface ClientService {

  List<Client> getAllClients();

  Client saveClient(ClientRequest clientRequest);
}
