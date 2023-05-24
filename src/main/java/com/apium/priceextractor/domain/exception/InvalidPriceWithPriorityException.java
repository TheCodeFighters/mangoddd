package com.apium.priceextractor.domain.exception;

public class InvalidPriceWithPriorityException extends DomainException {

  public InvalidPriceWithPriorityException(String message) {
    super("InvalidPriceWithPriorityException: " + message);
  }

}
