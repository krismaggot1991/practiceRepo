package com.pichincha.practice.config;

import static lombok.AccessLevel.PRIVATE;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@FieldDefaults(level = PRIVATE)
@Configuration
@ConfigurationProperties(prefix = "practice", ignoreUnknownFields = false)
public class PracticeProperties {

  Ws ws;

  @Data
  @FieldDefaults(level = PRIVATE)
  public static class Ws {

    String clientInformation;
  }
}
