package com.inditex.priceextractor.domain;

import java.util.UUID;

public record ProductId(UUID id) {

  public static ProductId fromString(String idString) {
    UUID id = UUID.fromString(idString);
    return new ProductId(id);
  }

  @Override
  public String toString() {
    return id.toString();
  }
}

