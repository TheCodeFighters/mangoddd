package com.inditex.priceextractor.domain;

import com.inditex.priceextractor.domain.exception.PositiveNumberException;

public record PositiveNumber(Double value) {

  public PositiveNumber {
    if (value < 0) {
      throw new PositiveNumberException("Value must be positive.");
    }
  }
}

