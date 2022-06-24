package com.pichincha.practice.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.practice.domain.dto.client.response.ClientResponse;
import com.pichincha.practice.util.TestDataInitializationHelper;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class PracticeApplicationIntegrationTest {

  public static final String URL_GET_ALL_CLIENTS = "/client/getAll";
  public static final String FORMAT_LOCALHOST = "http://localhost:%s%s%s";
  final ObjectMapper objectMapper = new ObjectMapper();

  @LocalServerPort
  int randomServerPort;
  @Autowired
  private TestDataInitializationHelper helper;
  @Value("${server.servlet.context-path}")
  private String serviceRoot;
  @Autowired
  private TestRestTemplate testRestTemplate;

  @BeforeEach
  public void init() {
    helper.initOffersTestDataSet();
  }

  @Test
  @DisplayName("Should do something")
  void shouldReturnValidClientsAndResponseHttpOkResponse() throws IOException {
    String url = String.format(
        FORMAT_LOCALHOST,
        randomServerPort,
        serviceRoot,
        URL_GET_ALL_CLIENTS
    );
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<String> getRequest = testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    List<ClientResponse> actualResponse = objectMapper.readValue(
        getRequest.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, ClientResponse.class));
    assertThat(getRequest.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assert.assertFalse(actualResponse.isEmpty());
    assertEquals(1, actualResponse.size());
    assertEquals(helper.getValidClient().getNameType(), actualResponse.get(0).getNameType());
  }
}
