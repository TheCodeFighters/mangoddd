package com.apium.priceextractor.infrastructure.eda.meesagerelay;

import java.util.List;

import com.apium.priceextractor.domain.DomainEventPublisher;
import com.apium.priceextractor.domain.event.DomainEvent;

public interface OutboxRepository extends DomainEventPublisher {

  @Override
  void send(DomainEvent event);

  public List<DomainEvent> findAllMessages();

  public void updateMessageStatus(DomainEvent event);
}
