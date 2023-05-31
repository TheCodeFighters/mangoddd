package com.apium.priceextractor.domain.exception;

public class EventTypeException extends DomainException {

  public EventTypeException(String message) {
    super("EventTypeException: " + message);
  }

}
