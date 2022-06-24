package com.pichincha.practice.common.exception;

import org.springframework.http.HttpStatus;

public class WSException extends RuntimeException {

  private final HttpStatus errorCode;

  public WSException(HttpStatus errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public HttpStatus getErrorCode() {
    return errorCode;
  }

}