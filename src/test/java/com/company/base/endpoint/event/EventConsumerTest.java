package com.company.base.endpoint.event;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.company.base.conf.FacadeTest;
import com.company.base.endpoint.event.gen.UuidCreated;
import com.company.base.endpoint.event.model.TypedUuidCreated;
import com.company.base.repository.DummyUuidRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EventConsumerTest extends FacadeTest {

  @Autowired EventConsumer subject;
  @Autowired DummyUuidRepository dummyUuidRepository;

  @Test
  void uuid_created_is_persisted() throws InterruptedException {
    var uuid = randomUUID().toString();

    subject.accept(
        List.of(
            new EventConsumer.AcknowledgeableTypedEvent(
                new TypedUuidCreated(UuidCreated.builder().uuid(uuid).build()), () -> {})));

    Thread.sleep(2_000);
    var saved = dummyUuidRepository.findById(uuid).orElseThrow();
    assertEquals(uuid, saved.getId());
  }
}
