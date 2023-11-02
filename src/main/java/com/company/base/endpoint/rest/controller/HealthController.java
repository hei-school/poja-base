package com.company.base.endpoint.rest.controller;

import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.company.base.repository.DummyRepository;
import com.company.base.repository.model.Dummy;

import java.util.List;

@RestController
@Value
public class HealthController {

  DummyRepository dummyRepository;

  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }

  @GetMapping("/dummy-table")
  public List<Dummy> dummyTable() {
    return dummyRepository.findAll();
  }
}
