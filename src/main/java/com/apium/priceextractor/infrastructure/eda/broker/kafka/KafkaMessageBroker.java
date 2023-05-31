package com.apium.priceextractor.infrastructure.eda.broker.kafka;

import java.util.List;

import com.apium.priceextractor.domain.event.DomainEvent;
import com.apium.priceextractor.infrastructure.eda.broker.MessageBroker;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaMessageBroker implements MessageBroker {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaMessageBroker(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessageToBroker(DomainEvent event) {
    kafkaTemplate.send(event.getTopicName().value(), event.getType());
  }

  @Override
  public List<DomainEvent> getEventsByTopic(String topic) {
    //TODO no implemented yet
    return null;
  }
}
