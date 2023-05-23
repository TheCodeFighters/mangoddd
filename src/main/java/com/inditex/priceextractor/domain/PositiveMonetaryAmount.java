package com.inditex.priceextractor.domain;

import com.inditex.priceextractor.domain.exception.PositiveMonetaryAmountException;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

public record PositiveMonetaryAmount(MonetaryAmount value) {

  public PositiveMonetaryAmount {
    if (value.getNumber().numberValue(Long.class) < 0) {
      throw new PositiveMonetaryAmountException("DomainError: monetary amount can not be negative");
    }
  }

  public static PositiveMonetaryAmount fromDoubleAndCurrency(Double value, String currency) {
    return new PositiveMonetaryAmount(Monetary.getDefaultAmountFactory().setCurrency(currency).setNumber(value).create());
  }
}
