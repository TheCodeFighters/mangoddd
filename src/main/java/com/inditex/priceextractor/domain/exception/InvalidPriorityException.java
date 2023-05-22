package com.inditex.priceextractor.domain.exception;

public class InvalidPriorityException extends DomainException {

  public InvalidPriorityException(String message) {
    super("InvalindException: " + message);
  }

}
