package com.company.base.endpoint.event;

import com.company.base.PojaGenerated;
import lombok.Getter;

import static java.lang.System.getenv;

@PojaGenerated
public enum EventBus {
    PRIMARY(getenv("AWS_PRIMARY_EVENTBRIDGE_BUS"), getenv("AWS_PRIMARY_SQS_QUEUE_URL")),
    SECONDARY(getenv("AWS_SECONDARY_EVENTBRIDGE_BUS"), getenv("AWS_SECONDARY_SQS_QUEUE_URL"));

    @Getter private final String busName;
    @Getter private final String sqsQueue;

    EventBus(String busName, String sqsQueue) {
        this.busName = busName;
        this.sqsQueue = sqsQueue;
    }
}
