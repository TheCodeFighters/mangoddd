package com.apium.priceextractor.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.apium.priceextractor.domain.exception.DateFormatException;

public record Date(java.util.Date date) {

  public static Date fromString(String value) {
    {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
      try {
        return new Date(simpleDateFormat.parse(value));
      } catch (ParseException e) {
        throw new DateFormatException("Date format is not valid");
      }
    }
  }

  public static Date Now() {
    return new Date(new java.util.Date());
  }
}
