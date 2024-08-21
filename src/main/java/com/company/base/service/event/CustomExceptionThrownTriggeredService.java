package com.company.base.service.event;

import com.company.base.endpoint.event.model.CustomExceptionThrownTriggered;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomExceptionThrownTriggeredService
    implements Consumer<CustomExceptionThrownTriggered> {
  @Override
  public void accept(CustomExceptionThrownTriggered customExceptionThrownTriggered) {
    if (customExceptionThrownTriggered.getNumber() % 2 == 0) {
      log.info("Oke");
    } else {
      log.error("Error");
      throw new RuntimeException("This an exception by Onitsiky");
    }
  }
}
