package com.apium.priceextractor.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.apium.priceextractor.domain.exception.DateRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateRangeTest {

  @Test
  @DisplayName("when valid dateRange Has been created the should work")
  public void test_0() {
    Date givenStartDate = Date.fromString("2020-06-14-00.00.00");
    Date givenEndDate = Date.fromString("2020-12-31-23.59.59");
    DateRange actualDateRange = new DateRange(givenStartDate, givenEndDate);
    assertEquals(givenStartDate, actualDateRange.startDate());
    assertEquals(givenEndDate, actualDateRange.endDate());
  }

  @Test
  @DisplayName("when invalid dateRange Has been created the should not work")
  public void test_1() {
    Date givenStartDate = Date.fromString("2020-06-14-00.00.00");
    Date givenEndDate = Date.fromString("2019-12-31-23.59.59");
    assertThrows(DateRangeException.class, () -> new DateRange(givenStartDate, givenEndDate));
  }

}