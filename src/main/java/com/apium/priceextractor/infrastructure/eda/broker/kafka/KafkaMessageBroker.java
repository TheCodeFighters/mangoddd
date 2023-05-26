package com.apium.priceextractor.infrastructure.eda.broker.kafka;

import com.apium.priceextractor.domain.event.DomainEvent;
import com.apium.priceextractor.infrastructure.eda.broker.MessageBroker;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaMessageBroker implements MessageBroker {

  private final KafkaTemplate<String, DomainEvent> kafkaTemplate;

  private final String topicName;

  public KafkaMessageBroker(KafkaTemplate<String, DomainEvent> kafkaTemplate, String topicName) {
    this.kafkaTemplate = kafkaTemplate;
    this.topicName = topicName;
  }

  @Override
  public DomainEvent sendMessageToBroker(DomainEvent event) {
    kafkaTemplate.send(topicName, event);
    return event;
  }
}
