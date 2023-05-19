package com.inditex.priceextractor.domain;

public class PositiveMonetaryAmountException extends DomainException {

  public PositiveMonetaryAmountException(String message) {
    super("DomainError: " + message);
  }
}
