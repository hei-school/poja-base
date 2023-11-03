package com.company.base.endpoint.event.model;

import com.company.base.PojaGenerated;
import com.company.base.endpoint.event.gen.UuidCreated;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.ToString;

@PojaGenerated
@AllArgsConstructor
@ToString
public class TypedUuidCreated implements TypedEvent {
  private final UuidCreated uuidCreated;

  @Override
  public String getTypeName() {
    return UuidCreated.class.getTypeName();
  }

  @Override
  public Serializable getPayload() {
    return uuidCreated;
  }
}
