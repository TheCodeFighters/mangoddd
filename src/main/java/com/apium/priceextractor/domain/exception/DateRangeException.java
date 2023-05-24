package com.apium.priceextractor.domain.exception;

public class DateRangeException extends DomainException {

  public DateRangeException(String message) {
    super("DomainError: " + message);
  }
}
