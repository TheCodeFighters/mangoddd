package com.apium.priceextractor.domain;

import com.apium.priceextractor.domain.exception.InvalidPriorityException;

public record Priority(Integer value) {

  public Priority {
    assertValueIsValid(value);
  }

  private void assertValueIsValid(Integer value) {
    if (!(value >= 0 && value <= 35)) {
      throw new InvalidPriorityException("DomainError: priority is not inside the range");
    }
  }
}
