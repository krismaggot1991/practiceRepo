package com.pichincha.practice.service.impl;

import com.pichincha.practice.domain.dto.client.request.ClientRequest;
import com.pichincha.practice.domain.jpa.Client;
import com.pichincha.practice.repository.ClientRepository;
import com.pichincha.practice.service.ClientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;

  @Override
  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  @Override
  public Client saveClient(ClientRequest clientRequest) {
    return clientRepository.save(Client.builder()
        .identification(clientRequest.getIdentification())
        .nameType("Quemado")
        .build());
  }
}
