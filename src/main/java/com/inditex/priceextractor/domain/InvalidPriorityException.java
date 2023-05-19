package com.inditex.priceextractor.domain;

public class InvalidPriorityException extends DomainException {

  public InvalidPriorityException(String message) {
    super("InvalindException: " + message);
  }

}
