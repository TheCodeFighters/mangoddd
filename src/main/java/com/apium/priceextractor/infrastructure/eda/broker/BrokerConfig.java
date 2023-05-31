package com.apium.priceextractor.infrastructure.eda.broker;

import com.apium.priceextractor.infrastructure.eda.broker.NoOpBroker.NoopBroker;
import com.apium.priceextractor.infrastructure.eda.broker.kafka.KafkaMessageBroker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class BrokerConfig {

  //  @Bean()
  //  public KafkaMessageBroker createKafkaBroker(KafkaTemplate<String, String> kafkaTemplate) {
  //    return new KafkaMessageBroker(kafkaTemplate);
  //  }

  @Bean()
  @Primary
  public NoopBroker createNoOpKafkaBroker() {
    return new NoopBroker();
  }

}
