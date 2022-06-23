package com.pichincha.practice.domain.jpa;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @NotEmpty(message = "nameType is empty")
  @NotNull(message = "nameType cant be null")
  String nameType;

  @NotEmpty(message = "identification is empty")
  @NotNull(message = "identification cant be null")
  @Size(min = 5, max = 10, message
      = "identification must be between 5 and 10 characters")
  String identification;
}
