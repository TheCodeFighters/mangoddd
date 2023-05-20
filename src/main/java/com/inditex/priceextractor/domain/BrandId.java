package com.inditex.priceextractor.domain;

import java.util.UUID;

public record BrandId(UUID id) {

  @Override
  public String toString() {
    return id.toString();
  }
}
