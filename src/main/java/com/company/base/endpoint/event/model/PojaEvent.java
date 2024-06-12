package com.company.base.endpoint.event.model;

import static com.company.base.endpoint.event.EventBus.PRIMARY;

import com.company.base.PojaGenerated;
import com.company.base.endpoint.event.EventBus;
import java.io.Serializable;
import java.time.Duration;
import lombok.Getter;

@PojaGenerated
public abstract class PojaEvent implements Serializable {
  protected PojaEvent(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  protected PojaEvent() {
    this.eventBus = PRIMARY;
  }

  public abstract Duration maxConsumerDuration();

  private Duration randomConsumerBackoffBetweenRetries() {
    return Duration.ofSeconds(maxConsumerBackoffBetweenRetries().toSeconds());
  }

  public abstract Duration maxConsumerBackoffBetweenRetries();

  public final Duration randomVisibilityTimeout() {
    var eventHandlerInitMaxDuration = Duration.ofSeconds(90); // note(init-visibility)
    return Duration.ofSeconds(
        eventHandlerInitMaxDuration.toSeconds()
            + maxConsumerDuration().toSeconds()
            + randomConsumerBackoffBetweenRetries().toSeconds());
  }

  @Getter protected final EventBus eventBus;
}
