package com.apium.priceextractor.infrastructure.eda.broker.kafka.ievent;

import com.apium.priceextractor.domain.event.DomainEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class DomainEventSerializer implements Serializer<DomainEvent> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public byte[] serialize(String topic, DomainEvent data) {
    try {
      return objectMapper.writeValueAsBytes(data);
    } catch (Exception e) {
      throw new RuntimeException("Error serializing DomainEventSerializer", e);
    }
  }
}