package com.company.base.endpoint.rest.controller.health;

import static com.company.base.endpoint.rest.controller.health.PingController.KO;
import static com.company.base.endpoint.rest.controller.health.PingController.OK;
import static java.lang.Thread.sleep;
import static java.util.UUID.randomUUID;

import com.company.base.PojaGenerated;
import com.company.base.endpoint.event.EventProducer;
import com.company.base.endpoint.event.model.DurablyFallibleUuidCreated;
import com.company.base.endpoint.event.model.PojaEvent;
import com.company.base.endpoint.event.model.UuidCreated;
import com.company.base.repository.DummyUuidRepository;
import com.company.base.repository.model.DummyUuid;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@PojaGenerated
@RestController
@AllArgsConstructor
public class HealthEventController {

  DummyUuidRepository dummyUuidRepository;
  EventProducer eventProducer;

  @GetMapping(value = "/health/event")
  public ResponseEntity<String> random_durably_fallible_uuid_are_fired_then_created(
      @RequestParam(defaultValue = "1") int nbEvent,
      @RequestParam(defaultValue = "2") int waitInSeconds)
      throws InterruptedException {
    if (nbEvent < 1 || nbEvent > 500) {
      throw new RuntimeException("nbEvent must be between 1 and 500");
    }
    var uuids = new ArrayList<String>();
    for (int i = 0; i < nbEvent; i++) {
      uuids.add(randomUUID().toString());
    }

    eventProducer.accept(
        uuids.stream()
            .map(
                uuid ->
                    (PojaEvent)
                        DurablyFallibleUuidCreated.builder()
                            .uuidCreated(new UuidCreated(uuid))
                            .failureRate(0.1)
                            .waitDurationBeforeConsumingInSeconds(waitInSeconds)
                            .build())
            .toList());

    sleep(20_000);
    var savedUuids = dummyUuidRepository.findAllById(uuids).stream().map(DummyUuid::getId).toList();
    return savedUuids.containsAll(uuids) ? OK : KO;
  }
}
