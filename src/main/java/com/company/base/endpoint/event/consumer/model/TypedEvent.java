package com.company.base.endpoint.event.consumer.model;

import com.company.base.PojaGenerated;
import com.company.base.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
