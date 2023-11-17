package com.company.base.endpoint.rest.controller;

import com.company.base.endpoint.rest.security.model.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class JusticeController {

  @PostMapping("/secret")
  public String logTheSecret(
      @AuthenticationPrincipal Principal principal, @RequestBody String requestBody) {
    log.info("principal={}, secret={}", principal, requestBody);
    return "Your secret if safe(?) with me!";
  }
}
