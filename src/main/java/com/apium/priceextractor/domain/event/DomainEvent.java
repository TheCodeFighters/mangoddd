package com.apium.priceextractor.domain.event;

import java.io.Serializable;

import com.apium.priceextractor.domain.Date;

public abstract class DomainEvent implements Serializable {

  private final EventId id;

  private final Date occurredOn;

  private final TopicName topicName;

  private final Record payload;

  public DomainEvent(EventId id, Date occurredOn, TopicName topicName, Record payload) {
    this.id = id;
    this.occurredOn = occurredOn;
    this.topicName = topicName;
    this.payload = payload;
  }

  public EventId getId() {
    return id;
  }

  public Date getOccurredOn() {
    return occurredOn;
  }

  public TopicName getTopicName() {
    return topicName;
  }

  public Record getPayload() {
    return payload;
  }

  public String getType() {
    return this.getClass().getName();
  }
}
