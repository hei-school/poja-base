package com.company.base.service.event;

import static java.util.UUID.randomUUID;

import com.company.base.PojaGenerated;
import com.company.base.endpoint.event.gen.UuidCreated;
import com.company.base.repository.DummyUuidRepository;
import com.company.base.repository.model.DummyUuid;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@PojaGenerated
@Service
@AllArgsConstructor
@Slf4j
public class UuidCreatedService implements Consumer<UuidCreated> {

  private final DummyUuidRepository dummyUuidRepository;
  private final Environment environment;

  @Override
  public void accept(UuidCreated uuidCreated) {
    log.info("begin processing uuid created with pool-size = {}", environment.getRequiredProperty("spring.datasource.hikari.maximum-pool-size"));
    var dummyUuid = new DummyUuid();
    dummyUuid.setId(uuidCreated.getUuid());
    dummyUuidRepository.save(dummyUuid);
    createAndFindNDummyUUIDS(100);
    log.info("end processing uuid created");
  }

  void createAndFindNDummyUUIDS(int n){
    for (int i = 0; i < n; i++) {
      var dummyUuid = new DummyUuid();
      dummyUuid.setId(randomUUID().toString());
      dummyUuidRepository.save(dummyUuid);
      dummyUuidRepository.findById(dummyUuid.getId())
        .orElseThrow(() -> new RuntimeException("retrieval failure at UUID nb " + n));
    }
  }
}
