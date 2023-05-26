package com.apium.priceextractor.infrastructure.eda.broker;

import com.apium.priceextractor.domain.event.DomainEvent;

public interface MessageBroker {

  DomainEvent sendMessageToBroker(DomainEvent event);
}
