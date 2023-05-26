package com.apium.priceextractor.domain.event;

import java.util.UUID;

public record EventId(UUID id) {

  public static EventId create() {
    UUID id = UUID.randomUUID();
    return new EventId(id);
  }

  public static EventId fromString(String idString) {
    UUID id = UUID.fromString(idString);
    return new EventId(id);
  }

  @Override
  public String toString() {
    return id.toString();
  }
}