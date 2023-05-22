package com.inditex.priceextractor.domain;

public class DiscountException extends DomainException {

  public DiscountException(String message) {
    super("DomainError: " + message);
  }
}
