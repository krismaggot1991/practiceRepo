package com.pichincha.practice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.practice.domain.jpa.Client;
import com.pichincha.practice.repository.ClientRepository;
import com.pichincha.practice.service.impl.ClientServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class ClientServiceTest {

  @InjectMocks
  ClientServiceImpl clientService;

  @Mock
  ClientRepository clientRepository;

  @Test
  void shouldRetrieveAllClients() {

    when(clientRepository.findAll()).thenReturn(getValidListClients());
    List<Client> listClients = clientService.getAllClients();
    assertNotNull(listClients);
    verify(clientRepository, times(1)).findAll();
    assertEquals(1, listClients.size());
  }

  private List<Client> getValidListClients() {
    List<Client> listClients = new ArrayList<>();

    listClients.add(Client.builder()
        .id(1L)
        .nameType("Enrique")
        .build());

    return listClients;
  }

}
