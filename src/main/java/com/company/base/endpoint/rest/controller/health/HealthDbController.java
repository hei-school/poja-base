package com.company.base.endpoint.rest.controller.health;

import static com.company.base.endpoint.rest.controller.health.PingController.KO;
import static com.company.base.endpoint.rest.controller.health.PingController.OK;

import com.company.base.PojaGenerated;
import com.company.base.repository.DummyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@PojaGenerated
@RestController
@AllArgsConstructor
@Slf4j
public class HealthDbController {

  DummyRepository dummyRepository;
  Environment environment;

  @GetMapping("/health/db")
  public ResponseEntity<String> dummyTable_should_not_be_empty() {
    log.info("FRONTAL HIKARI PROPERTIES {}", environment.getProperty("spring.datasource.hikari.maximum-pool-size"));
    return dummyRepository.findAll().isEmpty() ? KO : OK;
  }
}
