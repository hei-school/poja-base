package com.company.base.endpoint.event;

import com.company.base.PojaGenerated;
import com.company.base.endpoint.event.gen.UuidCreated;
import com.company.base.endpoint.event.model.TypedEvent;
import com.company.base.service.event.UuidCreatedService;
import java.io.Serializable;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@PojaGenerated
@AllArgsConstructor
@Component
@Slf4j
public class EventServiceInvoker implements Consumer<TypedEvent> {
  private final UuidCreatedService uuidCreatedService;

  @Override
  public void accept(TypedEvent typedEvent) {
    Serializable payload = typedEvent.getPayload();
    if (UuidCreated.class.getTypeName().equals(typedEvent.getTypeName())) {
      uuidCreatedService.accept((UuidCreated) payload);
    } else {
      log.error("Unexpected type for event={}", typedEvent);
    }
  }
}
