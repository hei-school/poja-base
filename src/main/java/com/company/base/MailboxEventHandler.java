package com.company.base;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.company.base.endpoint.event.EventConf;
import com.company.base.endpoint.event.EventConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.List;
import java.util.Map;

import static com.company.base.endpoint.event.EventConsumer.toAcknowledgeableTypedEvent;

@Slf4j
public class MailboxEventHandler implements RequestHandler<SQSEvent, String> {

  @Override
  public String handleRequest(SQSEvent event, Context context) {
    log.info("Received: event={}, context={}", event, context);
    List<SQSEvent.SQSMessage> messages = event.getRecords();
    log.info("SQS messages: {}", messages);

    ConfigurableApplicationContext applicationContext = applicationContext();
    EventConsumer eventConsumer = applicationContext.getBean(EventConsumer.class);
    EventConf eventConf = applicationContext.getBean(EventConf.class);
    SqsClient sqsClient = applicationContext.getBean(SqsClient.class);

    eventConsumer.accept(toAcknowledgeableTypedEvent(eventConf, sqsClient, messages));

    applicationContext.close();
    return "{message: ok}";
  }

  private ConfigurableApplicationContext applicationContext() {
    log.info("DATABASE_URL");
    log.info(System.getenv("DATABASE_URL"));
    SpringApplication application = new SpringApplication(PojaApplication.class);
    application.setDefaultProperties(Map.of(
        "spring.main.web-application-type", "none",
        "spring.datasource.url", System.getenv("DATABASE_URL"),
        "spring.datasource.username", System.getenv("DATABASE_USERNAME"),
        "spring.datasource.password", System.getenv("DATABASE_PASSWORD")
    ));
    return application.run();
  }
}
