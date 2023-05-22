package com.inditex.priceextractor.domain;

public record Discount(PositiveNumber percentage) {

  public Discount {
    if (percentage.value() > 50) {
      throw new DiscountException("Percentage must not be greater than 50%");
    }
  }

}
