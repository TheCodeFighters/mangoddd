package com.inditex.priceextractor.domain;

import javax.money.MonetaryAmount;

public record PositiveMonetaryAmount(MonetaryAmount value) {

  public PositiveMonetaryAmount {
    if (value.getNumber().numberValue(Long.class) < 0) {
      throw new PositiveMonetaryAmountException("DomainError: monetary amount can not be negative");
    }
  }
}
