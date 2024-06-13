package com.company.base.endpoint.event;

import static java.lang.System.getenv;

import com.company.base.PojaGenerated;
import lombok.Getter;

@PojaGenerated
public enum EventProps {
  PRIMARY(getenv("AWS_PRIMARY_EVENTBRIDGE_BUS"), getenv("AWS_PRIMARY_SQS_QUEUE_URL")),
  SECONDARY(getenv("AWS_SECONDARY_EVENTBRIDGE_BUS"), getenv("AWS_SECONDARY_SQS_QUEUE_URL"));

  @Getter private final String busName;
  @Getter private final String sqsQueueUrl;

  EventProps(String busName, String sqsQueueUrl) {
    this.busName = busName;
    this.sqsQueueUrl = sqsQueueUrl;
  }
}
