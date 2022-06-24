package com.pichincha.practice.service;

import com.pichincha.practice.domain.dto.clientInformation.response.ClientInformation;
import java.util.Optional;

public interface ExternalClientInformationService {

  Optional<ClientInformation> getClientInformation(String identification);
}
