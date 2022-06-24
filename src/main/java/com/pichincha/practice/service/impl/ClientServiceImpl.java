package com.pichincha.practice.service.impl;

import com.pichincha.practice.domain.dto.client.request.ClientRequest;
import com.pichincha.practice.domain.dto.clientInformation.response.ClientInformation;
import com.pichincha.practice.domain.jpa.Client;
import com.pichincha.practice.repository.ClientRepository;
import com.pichincha.practice.service.ClientService;
import com.pichincha.practice.service.ExternalClientInformationService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;
  private final ExternalClientInformationService externalClientInformationService;

  @Override
  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  @Override
  public Client saveClient(ClientRequest clientRequest) {
    Optional<ClientInformation> optionalClientInformation = externalClientInformationService.getClientInformation(clientRequest.getIdentification());

    String nameObtained = "";
    if (optionalClientInformation.isPresent()) {
      nameObtained = optionalClientInformation.get().getName();
    } else {
      nameObtained = "NOMBRE POR DEFECTO";
    }

    return clientRepository.save(Client.builder()
        .identification(clientRequest.getIdentification())
        .nameType(nameObtained)
        .build());
  }
}
