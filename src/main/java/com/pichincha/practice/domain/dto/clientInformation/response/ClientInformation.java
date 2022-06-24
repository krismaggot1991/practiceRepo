package com.pichincha.practice.domain.dto.clientInformation.response;

import static lombok.AccessLevel.PRIVATE;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ClientInformation {

  String identification;

  @SerializedName("nombre")
  String name;
}
