package com.inditex.priceextractor.domain;

import java.util.UUID;

public record ProductDiscountId(UUID id) {

  @Override
  public String toString() {
    return id.toString();
  }
}
