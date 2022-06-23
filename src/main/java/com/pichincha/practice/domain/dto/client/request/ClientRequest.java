package com.pichincha.practice.domain.dto.client.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientRequest {

  @NotEmpty(message = "identification is empty")
  @NotNull(message = "identification cant be null")
  @Size(min = 5, max = 10, message
      = "identification must be between 5 and 10 characters")
  String identification;
}
