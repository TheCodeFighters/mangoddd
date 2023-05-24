package com.apium.priceextractor.domain.exception;

public class PositiveMonetaryAmountException extends DomainException {

  public PositiveMonetaryAmountException(String message) {
    super("DomainError: " + message);
  }
}
