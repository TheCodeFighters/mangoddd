package com.apium.priceextractor.domain.exception;

public class TopicNameException extends DomainException {

  public TopicNameException(String message) {
    super("DomainError: " + message);
  }

}
