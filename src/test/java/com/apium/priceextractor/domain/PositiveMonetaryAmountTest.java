package com.apium.priceextractor.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.apium.priceextractor.domain.exception.PositiveMonetaryAmountException;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositiveMonetaryAmountTest {

  @Test
  @DisplayName("when monetary amount is positive the sould work properly")
  public void test_1() {
    MonetaryAmount givenMonetaryAmount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(35.40).create();
    PositiveMonetaryAmount actual = PositiveMonetaryAmount.fromDoubleAndCurrency(35.40, "EUR");
    assertEquals(givenMonetaryAmount, actual.value());
  }

  @Test
  @DisplayName("when monetary amount is negative then throw exception")
  public void test_2() {
    assertThrows(PositiveMonetaryAmountException.class, () ->
        PositiveMonetaryAmount.fromDoubleAndCurrency(-35.40, "EUR"));
  }

}