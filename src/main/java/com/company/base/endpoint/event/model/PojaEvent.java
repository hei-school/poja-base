package com.company.base.endpoint.event.model;

import com.company.base.PojaGenerated;
import com.company.base.endpoint.event.EventBus;
import lombok.Getter;

import java.io.Serializable;
import java.time.Duration;

import static com.company.base.endpoint.event.EventBus.PRIMARY;

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

  @Getter
  protected final EventBus eventBus;

}
