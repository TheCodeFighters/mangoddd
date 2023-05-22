package com.inditex.priceextractor.domain.exception;

public class DiscountException extends DomainException {

  public DiscountException(String message) {
    super("DomainError: " + message);
  }
}
