package com.pichincha.practice.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.pichincha.practice.domain.dto.client.request.ClientRequest;
import com.pichincha.practice.domain.dto.client.response.ClientResponse;
import com.pichincha.practice.util.TestDataInitializationHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
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
  public static final String URL_SAVE_CLIENT = "/client/saveClient";
  public static final String FORMAT_LOCALHOST = "http://localhost:%s%s%s";
  public static final String URl_ORQCLIENTES0002 = "/testChapter/api/ORQClientes0002/";
  public static final String UTF8JSON = "application/json;charset=UTF-8";
  @ClassRule
  public static final WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.options().port(8088).bindAddress("localhost"));
  final ObjectMapper objectMapper = new ObjectMapper();
  @LocalServerPort
  int randomServerPort;
  @Autowired
  private TestDataInitializationHelper helper;
  @Value("${server.servlet.context-path}")
  private String serviceRoot;
  @Autowired
  private TestRestTemplate testRestTemplate;

  @AfterAll
  public static void destroy() {
    if (wireMockRule.isRunning()) {
      wireMockRule.stop();
    }
  }

  @BeforeEach
  public void init() throws IOException {
    helper.initOffersTestDataSet();
    if (!wireMockRule.isRunning()) {
      wireMockRule.start();
    }
    wireMockRule.resetMappings();
    wireMockRule.resetScenarios();
    wireMockRule.resetRequests();
    mockServices();
  }

  private void mockServices() throws IOException {
    mockGet("basicClientInformationResponse/basicClientInformation-OK.json", URl_ORQCLIENTES0002, HttpStatus.OK.value()); /// get
  }

  private void mockGet(String jsonResponse, String url, int status) throws IOException {
    String expectedResponse = Files.readString(new ClassPathResource(jsonResponse).getFile().toPath());
    wireMockRule.stubFor(WireMock.get(WireMock.urlMatching(url))
        .willReturn(WireMock.aResponse()
            .withBody(expectedResponse)
            .withStatus(status)
            .withHeader(HttpHeaders.CONTENT_TYPE, UTF8JSON)));
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

  @Test
  @DisplayName("Should do something")
  void shouldReturnClientByDefaultAndHttpOkResponseWhenSaveClientIsRequested() throws JsonProcessingException {
    String url = String.format(
        FORMAT_LOCALHOST,
        randomServerPort,
        serviceRoot,
        URL_SAVE_CLIENT
    );
    ClientRequest jsonObject = ClientRequest.builder()
        .identification("1803750311")
        .build();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<ClientRequest> requestObject = new HttpEntity<>(jsonObject, headers);
    ResponseEntity<String> postRequest = testRestTemplate.postForEntity(url, requestObject, String.class);
    ClientResponse actualResponse = objectMapper.readValue(
        postRequest.getBody(), ClientResponse.class);
    assertThat(postRequest.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertEquals("NOMBRE POR DEFECTO", actualResponse.getNameType());
  }

  @Test
  @DisplayName("Should do something")
  void shouldReturnClientByDefaultAndHttpOkResponseWhenSaveClientIsRequested2() throws JsonProcessingException {
    String url = String.format(
        FORMAT_LOCALHOST,
        randomServerPort,
        serviceRoot,
        URL_SAVE_CLIENT
    );
    ClientRequest jsonObject = ClientRequest.builder()
        .identification("1234567890")
        .build();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<ClientRequest> requestObject = new HttpEntity<>(jsonObject, headers);
    ResponseEntity<String> postRequest = testRestTemplate.postForEntity(url, requestObject, String.class);
    ClientResponse actualResponse = objectMapper.readValue(
        postRequest.getBody(), ClientResponse.class);
    assertThat(postRequest.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertEquals("Jon Terry", actualResponse.getNameType());
  }


}
