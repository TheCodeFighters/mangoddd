package com.apium.priceextractor.domain;

import java.util.UUID;

public record ProductDiscountId(UUID id) {

  public static ProductDiscountId fromString(String idString) {
    UUID id = UUID.fromString(idString);
    return new ProductDiscountId(id);
  }

  @Override
  public String toString() {
    return id.toString();
  }
}
