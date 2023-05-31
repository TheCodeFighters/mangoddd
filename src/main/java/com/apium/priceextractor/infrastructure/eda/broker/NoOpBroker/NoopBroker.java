package com.apium.priceextractor.infrastructure.eda.broker.NoOpBroker;

import java.util.ArrayList;
import java.util.List;

import com.apium.priceextractor.domain.event.DomainEvent;
import com.apium.priceextractor.infrastructure.eda.broker.MessageBroker;

public class NoopBroker implements MessageBroker {

  private List<DomainEvent> events;

  public NoopBroker() {
    this.events = new ArrayList<>();
  }

  @Override
  public void sendMessageToBroker(DomainEvent event) {
    events.add(event);
  }

  @Override
  public List<DomainEvent> getEventsByTopic(String topic) {
    return events;
  }
}
