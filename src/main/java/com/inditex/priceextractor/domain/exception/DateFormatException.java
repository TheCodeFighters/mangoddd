package com.inditex.priceextractor.domain.exception;

public class DateFormatException extends DomainException {

  public DateFormatException(String message) {
    super("DomainError: " + message);
  }

}
