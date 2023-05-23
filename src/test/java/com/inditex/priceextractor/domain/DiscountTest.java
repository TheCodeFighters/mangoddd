package com.inditex.priceextractor.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.inditex.priceextractor.domain.exception.DiscountException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountTest {

  @Test
  @DisplayName("when Discount is inside the range then sould return discount value object")
  public void test_0() {
    PositiveNumber givenPercentage = new PositiveNumber(10d);
    Discount actualDiscount = new Discount(givenPercentage);
    assertEquals(givenPercentage, actualDiscount.percentage());
  }

  @Test
  @DisplayName("when Discount is outside the range then sould throw DiscountException")
  public void test_1() {
    PositiveNumber givenPercentage = new PositiveNumber(150d);
    assertThrows(DiscountException.class, () -> new Discount(givenPercentage));
  }

}