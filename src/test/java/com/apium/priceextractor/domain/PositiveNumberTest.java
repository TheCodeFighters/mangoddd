package com.apium.priceextractor.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.apium.priceextractor.domain.exception.PositiveNumberException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositiveNumberTest {

  @Test
  @DisplayName("when PositiveNumber is created from positive value then PositiveNumber value object is returned")
  public void test_0() {
    Double value = 150d;
    PositiveNumber actualPositiveNumber = new PositiveNumber(value);
    assertEquals(value, actualPositiveNumber.value());
  }

  @Test
  @DisplayName("when PositiveNumber is created from negative value then throw PositiveNumberException")
  public void test_1() {
    assertThrows(PositiveNumberException.class, () -> new PositiveNumber(-150d));
  }

}