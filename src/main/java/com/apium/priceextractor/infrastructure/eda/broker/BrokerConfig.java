package com.apium.priceextractor.infrastructure.eda.broker;

import com.apium.priceextractor.domain.event.DomainEvent;
import com.apium.priceextractor.infrastructure.eda.broker.NoOpBroker.NoopBroker;
import com.apium.priceextractor.infrastructure.eda.broker.kafka.KafkaMessageBroker;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class BrokerConfig {

  @Bean()
  @Primary
  public KafkaMessageBroker createKafkaBroker(KafkaTemplate<String, String> kafkaTemplate) {
    //    DefaultKafkaProducerFactory<String, String> producerFactory =
    //        new DefaultKafkaProducerFactory<>(
    //            kafkaProperties.buildProducerProperties(),
    //            new StringSerializer(),
    //            new StringSerializer()
    //        );
    //
    //    KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);
    return new KafkaMessageBroker(kafkaTemplate);
  }

  @Bean()

  public NoopBroker createNoOpKafkaBroker() {
    return new NoopBroker();
  }

}
