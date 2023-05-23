package com.inditex.priceextractor.domain.exception;

public class DomainEntityNotFoundException extends DomainException {

  public DomainEntityNotFoundException(String message) {
    super("DomainEntityNotFoundException: " + message);
  }

}
