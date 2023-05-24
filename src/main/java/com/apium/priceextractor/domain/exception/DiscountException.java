package com.apium.priceextractor.domain.exception;

public class DiscountException extends DomainException {

  public DiscountException(String message) {
    super("DomainError: " + message);
  }
}
