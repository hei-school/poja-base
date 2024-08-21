package com.company.base.endpoint.event.model;

import com.company.base.PojaGenerated;
import java.time.Duration;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@PojaGenerated
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class CustomExceptionThrownTriggered extends PojaEvent {
  private int number;

  @Override
  public Duration maxConsumerDuration() {
    return Duration.ofSeconds(60);
  }

  @Override
  public Duration maxConsumerBackoffBetweenRetries() {
    return Duration.ofSeconds(5);
  }
}
