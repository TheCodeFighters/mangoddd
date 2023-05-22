package com.inditex.priceextractor.domain;

public class PositiveNumberException extends DomainException {

  public PositiveNumberException(String message) {
    super("DomainError: " + message);
  }
}
