package com.pichincha.practice.config.loggin.enums;

import java.util.Arrays;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusCode {
  OK("0", "OK", HttpStatus.OK),
  CLIENT_INFORMATION_ERROR("12345", "Get client information error.",
      HttpStatus.INTERNAL_SERVER_ERROR),
  UNDEFINED("-1", "UNDEFINED", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String code;
  private final String message;
  private final HttpStatus httpStatus;

  StatusCode(String code, String message, HttpStatus httpStatus) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public static StatusCode findByCode(String code) {
    return Arrays.stream(values())
        .filter(it -> it.getCode().equals(code))
        .findFirst()
        .orElse(UNDEFINED);
  }
}
