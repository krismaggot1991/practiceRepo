package com.pichincha.practice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.practice.domain.dto.client.request.ClientRequest;
import com.pichincha.practice.domain.dto.clientInformation.response.ClientInformation;
import com.pichincha.practice.domain.jpa.Client;
import com.pichincha.practice.repository.ClientInformationRepository;
import com.pichincha.practice.repository.ClientRepository;
import com.pichincha.practice.service.impl.ClientServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(controllers = ClientServiceImpl.class)
class ClientServiceTest {

  @MockBean
  ExternalClientInformationService externalClientInformationService;
  @Autowired
  ClientServiceImpl clientService;
  @MockBean
  ClientRepository clientRepository;
  @MockBean
  ClientInformationRepository clientInformationRepository;
  int counter;

  @Test
  @DisplayName("Should do something")
  void shouldRetrieveAllClients() {
    when(clientRepository.findAll()).thenReturn(getValidListClients());
    List<Client> listClients = clientService.getAllClients();
    assertNotNull(listClients);
    verify(clientRepository, times(1)).findAll();
    assertEquals(1, listClients.size());
  }

  @Test
  @DisplayName("Should do something")
  void shouldSaveNewClient() {
    getCounter();
    when(clientRepository.save(any())).thenReturn(getValidClient());
    when(externalClientInformationService.getClientInformation(any())).thenReturn(Optional.of(ClientInformation.builder().build()));
    Client clientSaved = clientService.saveClient(ClientRequest.builder().build());
    setCounter(counter);
    assertNotNull(clientSaved);
    assertEquals("1803750312", clientSaved.getIdentification());
    verify(clientRepository, times(1)).save(any());
    verify(externalClientInformationService, times(1)).getClientInformation(any());
    Assertions.assertSame(1, counter);
  }

  private List<Client> getValidListClients() {
    List<Client> listClients = new ArrayList<>();
    listClients.add(getValidClient());
    return listClients;
  }

  private Client getValidClient() {
    return Client.builder()
        .id(1L)
        .nameType("Enrique")
        .identification("1803750312")
        .build();
  }

  private int getCounter() {
    return counter;
  }

  private void setCounter(int counter) {
    this.counter = counter + 1;
  }
}
