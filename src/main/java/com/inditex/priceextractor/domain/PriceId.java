package com.inditex.priceextractor.domain;

import java.util.UUID;

public record PriceId(UUID id) {

  public static PriceId fromString(String idString) {
    UUID id = UUID.fromString(idString);
    return new PriceId(id);
  }

  @Override
  public String toString() {
    return id.toString();
  }
}