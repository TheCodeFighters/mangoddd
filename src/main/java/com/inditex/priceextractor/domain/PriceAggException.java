package com.inditex.priceextractor.domain;

public class PriceAggException extends DomainException {

  public PriceAggException(String message) {
    super("DomainError: " + message);
  }
}
