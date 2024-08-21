package com.company.base.endpoint.rest.controller;

import com.company.base.endpoint.event.EventProducer;
import com.company.base.endpoint.event.model.CustomExceptionThrownTriggered;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ExceptionController {
  private final EventProducer<CustomExceptionThrownTriggered> eventProducer;

  @GetMapping("/exception")
  public String triggerException() {
    eventProducer.accept(List.of(CustomExceptionThrownTriggered.builder().build()));
    return "triggered";
  }
}
