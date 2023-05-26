package com.apium.priceextractor.domain;

import com.apium.priceextractor.domain.event.DomainEvent;

public interface DomainEventPublisher {

  void send(DomainEvent event);
}
