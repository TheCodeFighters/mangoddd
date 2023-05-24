package com.apium.priceextractor.domain;

import com.apium.priceextractor.domain.exception.DateRangeException;

public record DateRange(Date startDate, Date endDate) {

  public DateRange {
    assertDateRangeIsValid(startDate, endDate);
  }

  private void assertDateRangeIsValid(Date startDate, Date endDate) {
    if (!startDate.date().before(endDate.date())) {
      throw new DateRangeException("DomainError: startDate can not bee newer than endDate");
    }
  }
}