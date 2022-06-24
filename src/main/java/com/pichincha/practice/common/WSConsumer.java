package com.pichincha.practice.common;

import static lombok.AccessLevel.PRIVATE;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pichincha.practice.common.exception.WSException;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@FieldDefaults(level = PRIVATE)
public class WSConsumer {

  @Autowired
  RestTemplate restTemplate;
  Gson gson = new Gson();

  public <T> WSResponse<T> post(@NotNull String url, @Nullable Object payLoad, @NotNull HttpHeaders headers, Class<T> responseType)
      throws WSException {
    Assert.notNull(url, "url must not be null.");
    Assert.notNull(headers, "headers must not be null, but it can have an empty headers list.");

    try {
      String requestObj = gson.toJson(payLoad);
      ResponseEntity ret = restTemplate.exchange(url,
          HttpMethod.POST,
          new HttpEntity<>(requestObj, headers),
          String.class);

      T response = (ret.getBody() == null) ? null : gson.fromJson(ret.getBody().toString(), responseType);

      return WSResponse.<T>builder().httpResponse(ret).body(response).build();
    } catch (JsonSyntaxException e) {
      throw new WSException(HttpStatus.INTERNAL_SERVER_ERROR, "Problems processing the json content.");
    } catch (HttpClientErrorException e) {
      throw new WSException(e.getStatusCode(), e.getMessage());
    } catch (RestClientException e) {
      throw new WSException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }


  public <T> WSResponse<T> get(@NotNull String url, Map<String, String> params, @NotNull HttpHeaders headers, Class<T> responseType)
      throws WSException {
    Assert.notNull(url, "URL must not be null.");
    Assert.notNull(params, "params must not be null, but it can be empty.");
    Assert.notNull(headers, "headers must not be null, but it can have an empty headers list.");

    if (params.isEmpty() && !url.endsWith("/")) {
      url = String.format("%s/", url);
    }

    try {
      ResponseEntity ret = restTemplate.exchange(url,
          HttpMethod.GET,
          new HttpEntity<String>(null, headers),
          String.class,
          params);

      T response = (ret.getBody() == null) ? null : gson.fromJson(ret.getBody().toString(), responseType);

      return WSResponse.<T>builder().httpResponse(ret).body(response).build();
    } catch (JsonSyntaxException e) {
      throw new WSException(HttpStatus.INTERNAL_SERVER_ERROR, "Problems processing the json content.");
    } catch (HttpClientErrorException e) {
      throw new WSException(e.getStatusCode(), e.getMessage());
    } catch (RestClientException e) {
      throw new WSException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

  }


}
