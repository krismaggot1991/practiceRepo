package com.pichincha.practice.repository;

import com.pichincha.practice.domain.dto.clientInformation.response.ClientInformation;
import java.util.Optional;

public interface ClientInformationRepository {

  Optional<ClientInformation> getClientInformation(String identification);

}
