package com.apium.priceextractor.domain;

import java.util.UUID;

public record BrandId(UUID id) {

  public static BrandId fromString(String idString) {
    UUID id = UUID.fromString(idString);
    return new BrandId(id);
  }

  @Override
  public String toString() {
    return id.toString();
  }
}
