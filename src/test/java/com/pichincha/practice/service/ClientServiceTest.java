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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(controllers = ClientServiceImpl.class)
class ClientServiceTest {

  @Autowired
  ClientServiceImpl clientService;

  @MockBean
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
