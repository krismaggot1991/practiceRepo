package com.pichincha.practice.service.mapper;

import com.pichincha.practice.domain.dto.client.response.ClientResponse;
import com.pichincha.practice.domain.jpa.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

  ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

  Client toEntity(ClientResponse clientResponse);

  ClientResponse toDTO(Client client);

  default List<ClientResponse> toDTOList(List<Client> listClients) {
    if (listClients == null) {
      return new ArrayList<>();
    }
    return listClients.stream().map(this::toDTO).collect(Collectors.toList());
  }

}
