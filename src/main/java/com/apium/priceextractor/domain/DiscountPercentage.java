package com.apium.priceextractor.domain;

import com.apium.priceextractor.domain.exception.DiscountException;

public record DiscountPercentage(PositiveNumber percentage) {

  public DiscountPercentage {
    if (percentage.value() > 50) {
      throw new DiscountException("Percentage must not be greater than 50%");
    }
  }

}
