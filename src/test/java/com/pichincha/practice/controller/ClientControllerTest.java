package com.pichincha.practice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.practice.domain.dto.client.response.ClientResponse;
import com.pichincha.practice.service.ClientService;
import com.pichincha.practice.service.mapper.ClientMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {

  final ObjectMapper objectMapper = new ObjectMapper();

  @MockBean
  ClientService clientService;
  @Autowired
  MockMvc mockMvc;
  @MockBean
  ClientMapper clientMapper;

  @Test
  void shouldRetrieveAllClientsAndReturnOkStatus() throws Exception {
    String url = String.format("/client/getAll");

    ClientResponse clientResponse = ClientResponse.builder()
        .id(1L)
        .nameType("Juan")
        .build();
    ClientResponse clientResponse2 = ClientResponse.builder()
        .id(2L)
        .nameType("Pedro")
        .build();

    List<ClientResponse> listClientResponse = new ArrayList<>();
    listClientResponse.add(clientResponse);
    listClientResponse.add(clientResponse2);

    when(clientService.getAllClients()).thenReturn(new ArrayList<>());
    when(clientMapper.toDTOList(any())).thenReturn(listClientResponse);
    mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(result -> {
          List<ClientResponse> actualResponse = objectMapper.readValue(
              result.getResponse().getContentAsString(), objectMapper.getTypeFactory().constructCollectionType(List.class, ClientResponse.class));
          verify(clientService, times(1)).getAllClients();
          verify(clientMapper, times(1)).toDTOList(any());
          assertNotNull(actualResponse);
          assertEquals(2, actualResponse.size());
          assertEquals("Pedro", actualResponse.get(1).getNameType());
        });
  }


}
