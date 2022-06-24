package com.pichincha.practice.repository.impl;

import com.pichincha.practice.common.WSConsumer;
import com.pichincha.practice.config.PracticeProperties;
import com.pichincha.practice.config.loggin.EventLog;
import com.pichincha.practice.config.loggin.enums.EventLogName;
import com.pichincha.practice.config.loggin.enums.EventLogService;
import com.pichincha.practice.config.loggin.enums.EventLogType;
import com.pichincha.practice.config.loggin.enums.StatusCode;
import com.pichincha.practice.domain.dto.clientInformation.response.ClientInformation;
import com.pichincha.practice.domain.dto.clientInformation.response.ClientInformationResponse;
import com.pichincha.practice.exception.HttpException;
import com.pichincha.practice.repository.ClientInformationRepository;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ClientInformationRepositoryImpl implements ClientInformationRepository {

  private final PracticeProperties practiceProperties;
  private final WSConsumer consumer;

  @Override
  public Optional<ClientInformation> getClientInformation(String identification) {
    EventLog eventLog = new EventLog(identification, EventLogName.GET_CLIENT_INFORMATION.name(), EventLogType.REQUEST.name(),
        EventLogService.CLIENT.name(), identification);
    log.info(eventLog);
    try {
      ClientInformationResponse response = consumer.get(practiceProperties.getWs().getClientInformation(), Collections.emptyMap(), new HttpHeaders(),
          ClientInformationResponse.class).getBody();
      return response.getClientInformation().stream()
          .filter(clientInformation -> clientInformation.getIdentification().equals(identification)).findAny();
    } catch (Exception e) {
      eventLog.setResponse(e, StatusCode.CLIENT_INFORMATION_ERROR);
      log.error(eventLog);
      throw new HttpException(StatusCode.CLIENT_INFORMATION_ERROR);
    }
  }
}
