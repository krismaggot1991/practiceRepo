package com.pichincha.practice.controller;

import com.pichincha.practice.domain.dto.client.request.ClientRequest;
import com.pichincha.practice.domain.dto.client.response.ClientResponse;
import com.pichincha.practice.service.ClientService;
import com.pichincha.practice.service.mapper.ClientMapper;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Log4j2
public class ClientController {

  private final ClientService clientService;
  private final ClientMapper clientMapper;

  @GetMapping("/getAll")
  @Produces("application/json")
  public ResponseEntity<List<ClientResponse>> getAllClients() {
    return new ResponseEntity<>(clientMapper.toDTOList(clientService.getAllClients()), HttpStatus.OK);
  }

  @PostMapping("/saveClient")
  @Produces("application/json")
  public ResponseEntity<ClientResponse> saveClient(@RequestBody @Valid ClientRequest clientRequest) {
    return new ResponseEntity<>(clientMapper.toDTO(clientService.saveClient(clientRequest)), HttpStatus.OK);
  }
}
