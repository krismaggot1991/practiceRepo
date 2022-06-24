package com.pichincha.practice.exception;

import com.pichincha.practice.config.loggin.enums.StatusCode;
import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {

  private final String errorCode;
  private final HttpStatus httpStatus;

  public HttpException(StatusCode statusCode) {
    super(statusCode.getMessage());
    this.errorCode = statusCode.getCode();
    this.httpStatus = statusCode.getHttpStatus();
  }

  public String getErrorCode() {
    return errorCode;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}