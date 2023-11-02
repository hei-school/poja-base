package com.company.base.endpoint.rest.controller;

import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.company.base.endpoint.event.EventProducer;
import com.company.base.endpoint.event.gen.UuidCreated;
import com.company.base.endpoint.event.model.TypedUuidCreated;
import com.company.base.repository.DummyRepository;
import com.company.base.repository.model.Dummy;

import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@Value
public class HealthController {

  DummyRepository dummyRepository;
  EventProducer eventProducer;

  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }

  @GetMapping("/dummy-table")
  public List<Dummy> dummyTable() {
    return dummyRepository.findAll();
  }

  @GetMapping("/uuid-created")
  public TypedUuidCreated uuidCreated() {
    var event = new TypedUuidCreated(new UuidCreated().toBuilder().uuid(randomUUID().toString()).build());
    eventProducer.accept(List.of(event));
    return event;
  }
}
