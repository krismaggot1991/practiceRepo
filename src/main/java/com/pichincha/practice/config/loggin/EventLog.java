package com.pichincha.practice.config.loggin;

import static lombok.AccessLevel.PRIVATE;

import com.pichincha.practice.config.loggin.enums.EventLogSystemType;
import com.pichincha.practice.config.loggin.enums.EventLogType;
import com.pichincha.practice.config.loggin.enums.StatusCode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EventLog {

  String identification;
  String event;
  String eventType;
  String registerDate;
  String targetServiceName;
  String code;
  String message;
  Object data;
  Object dataResponse;
  String customerType;

  public EventLog(String identification, String event, String eventType, String targetServiceName,
      Object data) {
    super();
    this.identification = identification;
    this.event = event;
    this.eventType = eventType;
    this.registerDate = LocalDateTime.now().toString();
    this.targetServiceName = targetServiceName;
    this.data = data;
  }

  public EventLog(String event, String eventType, String targetServiceName, Object data) {
    super();
    this.event = event;
    this.eventType = eventType;
    this.registerDate = LocalDateTime.now().toString();
    this.targetServiceName = targetServiceName;
    this.data = data;
  }

  public void setResponse(Object dataResponse, StatusCode statusCode) {
    this.registerDate = LocalDateTime.now().toString();
    this.eventType = EventLogType.RESPONSE.name();
    this.dataResponse = dataResponse;
    if (statusCode != null) {
      this.code = EventLogSystemType.DATA.getValue().concat(statusCode.getCode());
      this.message = EventLogSystemType.DATA.getValue().concat(statusCode.getMessage());
    }
  }

  public void setRequest(Object data) {
    this.registerDate = LocalDateTime.now().toString();
    this.eventType = EventLogType.REQUEST.name();
    this.data = data;
    this.code = null;
    this.message = null;
  }

  public void setResult(String result) {
    this.registerDate = LocalDateTime.now().toString();
    this.eventType = EventLogType.RESULT_VALIDATION.name();
    this.message = result;
    this.code = null;
    this.dataResponse = null;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
