package com.company.base.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.company.base.endpoint.event.gen.UuidCreated;

import java.util.function.Consumer;

@Service
@AllArgsConstructor
@Slf4j
public class UuidCreatedService implements Consumer<UuidCreated> {

  @Override
  public void accept(UuidCreated uuidCreated) {
    log.info("Asynchronously received: uuidCreated={}.", uuidCreated);
  }
}