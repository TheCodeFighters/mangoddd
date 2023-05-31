package com.apium.priceextractor.infrastructure.eda.broker;

import java.util.List;

import com.apium.priceextractor.domain.event.DomainEvent;

public interface MessageBroker {

  void sendMessageToBroker(DomainEvent event);

  List<DomainEvent> getEventsByTopic(String topic);
}
