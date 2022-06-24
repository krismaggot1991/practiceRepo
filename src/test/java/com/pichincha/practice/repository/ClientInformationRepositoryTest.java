package com.pichincha.practice.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.Gson;
import com.pichincha.practice.common.WSConsumer;
import com.pichincha.practice.common.WSResponse;
import com.pichincha.practice.config.PracticeProperties;
import com.pichincha.practice.domain.dto.clientInformation.response.ClientInformation;
import com.pichincha.practice.domain.dto.clientInformation.response.ClientInformationResponse;
import com.pichincha.practice.repository.impl.ClientInformationRepositoryImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = PracticeProperties.class)
@WebMvcTest(controllers = ClientInformationRepositoryImpl.class)
class ClientInformationRepositoryTest {
  
  private final Gson gson = new Gson();
  @Autowired
  ClientInformationRepository clientInformationRepository;
  @MockBean
  private WSConsumer consumerForClient;

  @Test
  @DisplayName("Should do something")
  void shouldGetValidClientInformation() throws IOException {
    String expectedResponse = Files.readString(
        new ClassPathResource("basicClientInformationResponse/basicClientInformation-OK.json").getFile()
            .toPath());
    doReturn(WSResponse.<ClientInformationResponse>builder().body(gson.fromJson(expectedResponse, ClientInformationResponse.class)).build())
        .when(consumerForClient)
        .get(any(), any(), any(), any());
    Optional<ClientInformation> optionalClientInformation = clientInformationRepository.getClientInformation("1803750312");
    Assertions.assertNotNull(optionalClientInformation);
    Assertions.assertEquals("Christian", optionalClientInformation.get().getName());
  }

}
