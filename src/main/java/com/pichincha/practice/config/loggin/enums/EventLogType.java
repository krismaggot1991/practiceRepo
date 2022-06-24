package com.pichincha.practice.config.loggin.enums;

import lombok.Getter;

@Getter
public enum EventLogType {
  RESPONSE,
  REQUEST,
  INTERNAL_PROCESS,
  RESULT_VALIDATION
}
