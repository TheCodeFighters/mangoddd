package com.apium.priceextractor.domain.exception;

public class PositiveNumberException extends DomainException {

  public PositiveNumberException(String message) {
    super("DomainError: " + message);
  }
}
