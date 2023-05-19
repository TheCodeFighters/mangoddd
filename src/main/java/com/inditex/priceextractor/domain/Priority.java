package com.inditex.priceextractor.domain;

public class Priority {

  private Integer value;

  public Priority(Integer value) {
    assertValueIsValid(value);
    this.value = value;
  }

  private void assertValueIsValid(Integer value) {
    if (!(value >= 0 && value <= 35)) {
      throw new InvalidPriorityException("DomainError: priority is not inside the range");
    }
  }

  public Integer getValue() {
    return value;
  }
}
