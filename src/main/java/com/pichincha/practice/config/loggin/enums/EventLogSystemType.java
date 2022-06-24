package com.pichincha.practice.config.loggin.enums;

import lombok.Getter;

@Getter
public enum EventLogSystemType {

  DATA("DATA-"), HTTP("HTTP-");

  private final String value;

  EventLogSystemType(String value) {
    this.value = value;
  }
}
