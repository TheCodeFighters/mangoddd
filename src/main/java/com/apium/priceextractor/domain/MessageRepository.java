package com.apium.priceextractor.domain;

import com.apium.priceextractor.domain.event.DomainEvent;

public interface MessageRepository {

  void sendARMessageOrFail(DomainEvent event);
}
