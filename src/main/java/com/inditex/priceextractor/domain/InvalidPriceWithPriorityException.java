package com.inditex.priceextractor.domain;

public class InvalidPriceWithPriorityException extends DomainException {

  public InvalidPriceWithPriorityException(String message) {
    super("InvalidPriceWithPriorityException: " + message);
  }

}
