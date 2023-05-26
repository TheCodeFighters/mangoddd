package com.apium.priceextractor.domain.event;

public abstract class DomainEvent {

  private final EventId id;

  private final Record payload;

  public DomainEvent(EventId id, Record payload) {
    this.id = id;
    this.payload = payload;
  }

  public EventId getId() {
    return id;
  }

  public Record getPayload() {
    return payload;
  }

  //  abstract String serialize();

  //  protected abstract String serializePayload();
  //
  //  abstract DomainEvent unSerialize(String plainEvent);
  //
  //  protected abstract DomainEvent unSerializePayload(String plainEvent);
}
