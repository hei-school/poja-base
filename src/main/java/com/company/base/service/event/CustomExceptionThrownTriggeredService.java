package com.company.base.service.event;

import com.company.base.endpoint.event.model.CustomExceptionThrownTriggered;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

@Service
public class CustomExceptionThrownTriggeredService
    implements Consumer<CustomExceptionThrownTriggered> {
  @Override
  public void accept(CustomExceptionThrownTriggered customExceptionThrownTriggered) {
    throw new RuntimeException("This an exception by Onitsiky");
  }
}
