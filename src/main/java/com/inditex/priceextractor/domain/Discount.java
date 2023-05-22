package com.inditex.priceextractor.domain;

import com.inditex.priceextractor.domain.exception.DiscountException;

public record Discount(PositiveNumber percentage) {

  public Discount {
    if (percentage.value() > 50) {
      throw new DiscountException("Percentage must not be greater than 50%");
    }
  }

}
