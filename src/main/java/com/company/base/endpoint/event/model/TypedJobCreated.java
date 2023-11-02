package com.company.base.endpoint.event.model;

import api.bpartners.annotator.endpoint.event.gen.JobCreated;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class TypedJobCreated implements TypedEvent {
  private final JobCreated jobCreated;

  @Override
  public String getTypeName() {
    return JobCreated.class.getTypeName();
  }

  @Override
  public Serializable getPayload() {
    return jobCreated;
  }
}
