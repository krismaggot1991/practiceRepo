package com.pichincha.practice.service.impl;

import com.pichincha.practice.domain.dto.clientInformation.response.ClientInformation;
import com.pichincha.practice.repository.ClientInformationRepository;
import com.pichincha.practice.service.ExternalClientInformationService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExternalClientInformationServiceImpl implements ExternalClientInformationService {

  private final ClientInformationRepository clientInformationRepository;

  @Override
  public Optional<ClientInformation> getClientInformation(String identification) {
    return clientInformationRepository.getClientInformation(identification);
  }
}
