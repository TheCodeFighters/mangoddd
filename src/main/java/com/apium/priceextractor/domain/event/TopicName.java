package com.apium.priceextractor.domain.event;

public record TopicName(String value) {

  private static final String TOPIC_NAME_PREFIX = "priceextractor.";

  public TopicName(String value) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Topic name cannot be null or empty");
    }
    this.value = TOPIC_NAME_PREFIX + value;
  }
}
