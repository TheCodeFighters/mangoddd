package com.apium.priceextractor.infrastructure.eda.messagerelay;

import com.apium.priceextractor.domain.event.DomainEvent;
import com.apium.priceextractor.infrastructure.eda.broker.MessageBroker;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageRelayService {

  private final OutboxRepository outboxRepository;

  private final MessageBroker messageBroker;

  public MessageRelayService(OutboxRepository outboxRepository, MessageBroker messageBroker) {
    this.outboxRepository = outboxRepository;
    this.messageBroker = messageBroker;
  }

  @Scheduled(fixedDelay = 30000)
  public void processMessages() {
    outboxRepository.findAllMessages()
        .forEach(this::processMessage);
  }

  @Transactional
  public void processMessage(DomainEvent domainEvent) {
    outboxRepository.updateMessageStatus(domainEvent);
    messageBroker.sendMessageToBroker(domainEvent);
  }
}
