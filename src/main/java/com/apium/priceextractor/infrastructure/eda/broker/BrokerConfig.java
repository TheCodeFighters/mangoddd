package com.apium.priceextractor.infrastructure.eda.broker;

import com.apium.priceextractor.domain.event.DomainEvent;
import com.apium.priceextractor.infrastructure.eda.broker.kafka.KafkaMessageBroker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class BrokerConfig {

  @Bean()
  public KafkaMessageBroker springDataPriceRepository(KafkaTemplate<String, DomainEvent> kafkaTemplate) {
    return new KafkaMessageBroker(kafkaTemplate, "price-topic");
  }

}
