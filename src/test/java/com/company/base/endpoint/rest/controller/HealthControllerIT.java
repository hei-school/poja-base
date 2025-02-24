package com.company.base.endpoint.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.company.base.PojaGenerated;
import com.company.base.conf.FacadeIT;
import com.company.base.endpoint.rest.controller.health.PingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@PojaGenerated
class HealthControllerIT extends FacadeIT {

  @Autowired PingController pingController;

  @Test
  void ping() {
    assertEquals("pong", pingController.ping());
  }
}
