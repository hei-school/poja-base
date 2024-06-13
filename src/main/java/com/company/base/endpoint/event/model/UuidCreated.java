package com.company.base.endpoint.event.model;

import com.company.base.PojaGenerated;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@PojaGenerated
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
@AllArgsConstructor
public class UuidCreated extends PojaEvent {
  @JsonProperty("uuid")
  private String uuid;

  @Override
  public Duration maxConsumerDuration() {
    return Duration.ofSeconds(10);
  }

  @Override
  public Duration maxConsumerBackoffBetweenRetries() {
    return Duration.ofSeconds(30);
  }
}
