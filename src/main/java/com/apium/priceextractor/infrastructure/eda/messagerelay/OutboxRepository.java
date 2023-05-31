package com.apium.priceextractor.infrastructure.eda.messagerelay;

import java.util.List;

import com.apium.priceextractor.domain.DomainEventPublisher;
import com.apium.priceextractor.domain.event.DomainEvent;

public interface OutboxRepository extends DomainEventPublisher {

  @Override
  void publish(DomainEvent event);

  List<DomainEvent> findAllMessages();

  void updateMessageStatus(DomainEvent message);
}
