package com.company.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class PojaApplication {

  public static void main(String[] args) {
    SpringApplication.run(PojaApplication.class, args);
  }

  @Bean
  ApplicationRunner applicationRunner(Environment environment) {
    return args -> {
      log.info("env=" + environment);
    };
  }

}
