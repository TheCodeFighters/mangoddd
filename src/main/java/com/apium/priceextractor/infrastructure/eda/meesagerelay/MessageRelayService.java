package com.apium.priceextractor.infrastructure.eda.meesagerelay;

import com.apium.priceextractor.infrastructure.eda.broker.MessageBroker;
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
    outboxRepository.findAllMessages().stream()
        .map(messageBroker::sendMessageToBroker)
        .forEach(outboxRepository::updateMessageStatus);
  }
}
