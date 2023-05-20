package com.inditex.priceextractor.domain;

import java.util.UUID;

public record ProductId(UUID id) {

  @Override
  public String toString() {
    return id.toString();
  }
}

