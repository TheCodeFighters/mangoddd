package com.inditex.priceextractor.domain.exception;

public class PriceAggException extends DomainException {

  public PriceAggException(String message) {
    super("DomainError: " + message);
  }
}
