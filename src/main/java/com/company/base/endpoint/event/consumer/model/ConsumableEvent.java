package com.company.base.endpoint.event.consumer.model;

import com.company.base.PojaGenerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

@PojaGenerated
@AllArgsConstructor
public class ConsumableEvent {
  @Getter private final TypedEvent event;
  private final Runnable acknowledger;
  private final Runnable randomVisibilityTimeoutSetter;

  public void ack() {
    acknowledger.run();
  }

  public void newRandomVisibilityTimeout() {
    randomVisibilityTimeoutSetter.run();
  }
}
