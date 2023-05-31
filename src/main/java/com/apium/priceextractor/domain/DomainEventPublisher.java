package com.apium.priceextractor.domain;

import com.apium.priceextractor.domain.event.DomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DomainEventPublisher {

  void publish(DomainEvent event);
}
