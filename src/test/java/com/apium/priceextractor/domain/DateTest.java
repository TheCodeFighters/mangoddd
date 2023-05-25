package com.apium.priceextractor.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.apium.priceextractor.domain.exception.DateFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateTest {

  @Test
  @DisplayName("when valid dateRange Has been created the should work")
  public void test_0() throws ParseException {
    String givenDateString = "2020-06-14-00.00.00";
    Date actualDate = Date.fromString(givenDateString);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
    assertEquals(simpleDateFormat.parse(givenDateString), actualDate.date());
  }

  @Test
  @DisplayName("when invalid Date has been provided then should throw DateFormatException")
  public void test_1() {
    String givenDateString = "2sdfgdsfgd";

    assertThrows(DateFormatException.class, () -> Date.fromString(givenDateString));
  }

}
