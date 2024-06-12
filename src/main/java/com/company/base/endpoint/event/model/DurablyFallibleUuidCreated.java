package com.company.base.endpoint.event.model;

import static java.lang.Math.random;

import com.company.base.PojaGenerated;
import com.company.base.endpoint.event.EventBus;
import java.time.Duration;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@PojaGenerated
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class DurablyFallibleUuidCreated extends PojaEvent {
  private UuidCreated uuidCreated;
  private int waitDurationBeforeConsumingInSeconds;
  private double failureRate;

  @Builder
  public DurablyFallibleUuidCreated(
      EventBus eventBus,
      UuidCreated uuidCreated,
      int waitDurationBeforeConsumingInSeconds,
      double failureRate) {
    super(eventBus);
    this.uuidCreated = uuidCreated;
    this.waitDurationBeforeConsumingInSeconds = waitDurationBeforeConsumingInSeconds;
    this.failureRate = failureRate;
  }

  public boolean shouldFail() {
    return random() < failureRate;
  }

  @Override
  public Duration maxConsumerDuration() {
    return Duration.ofSeconds(
        waitDurationBeforeConsumingInSeconds + uuidCreated.maxConsumerDuration().toSeconds());
  }

  @Override
  public Duration maxConsumerBackoffBetweenRetries() {
    return uuidCreated.maxConsumerBackoffBetweenRetries();
  }
}
