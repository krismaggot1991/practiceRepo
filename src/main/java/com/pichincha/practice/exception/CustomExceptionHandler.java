package com.pichincha.practice.exception;

import static com.pichincha.practice.util.Constant.HTTP_ERROR_HEADER;
import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@FieldDefaults(level = PRIVATE)
public class CustomExceptionHandler {
    
  @ExceptionHandler(HttpException.class)
  public ResponseEntity handleHttpException(HttpException exception) {
    return ResponseEntity.status(exception.getHttpStatus()).header(HTTP_ERROR_HEADER, exception.getErrorCode()).build();
  }

}
