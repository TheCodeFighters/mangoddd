package com.apium.priceextractor.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.apium.priceextractor.domain.exception.InvalidPriorityException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriorityTest {

  @Test
  @DisplayName("given a valid priority, then return a priority object")
  public void test_1() {
    Priority priority = new Priority(1);
    assertEquals(1, priority.value());
  }

  @Test
  @DisplayName("given a priority that is inside the valid range, then return a InvalidPriorityException")
  public void test_2() {
    assertThrows(InvalidPriorityException.class, () -> new Priority(50));
  }

}