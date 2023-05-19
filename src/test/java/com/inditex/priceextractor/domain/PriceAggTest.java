package com.inditex.priceextractor.domain;

import static org.junit.Assert.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.inditex.priceextractor.infrastructure.format.date.SimpleDateFormatConfig;

import javax.money.Monetary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PriceAggTest {

  private SimpleDateFormat simpleDateFormat;

  @BeforeEach
  public void setup() {
    simpleDateFormat = new SimpleDateFormatConfig().simpleDateFormat();
  }

  @Test
  public void givenValidData_thenShouldWork() throws ParseException {
    PriceId givenPriceId = new PriceId(UUID.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b"));
    Long givenBrandId = 2L;
    Date givenStartDate = simpleDateFormat.parse("2020-06-14-00.00.00");
    Date givenEndDate = simpleDateFormat.parse("2020-12-31-23.59.59");
    Long givenProductId = 35455L;
    Priority givenPriority = new Priority(0);
    PositiveMonetaryAmount givenpositiveMonetaryAmount =
        new PositiveMonetaryAmount(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(35.40).create());

    PriceAgg priceAggregate = new PriceAgg(
        givenPriceId,
        givenBrandId,
        givenStartDate,
        givenEndDate,
        givenProductId,
        givenPriority,
        givenpositiveMonetaryAmount
    );

    Assertions.assertEquals(givenPriceId, priceAggregate.getId());
    Assertions.assertEquals(givenBrandId, priceAggregate.getBrandId());
    Assertions.assertEquals(givenStartDate, priceAggregate.getStartDate());
    Assertions.assertEquals(givenEndDate, priceAggregate.getEndDate());
    Assertions.assertEquals(givenProductId, priceAggregate.getProductId());
    Assertions.assertEquals(givenPriority, priceAggregate.getPriority());
    Assertions.assertEquals(givenpositiveMonetaryAmount, priceAggregate.getPositiveMonetaryAmount());
  }

  @Test
  public void givenEndDateNewerThanStartDate_thenThrowRunTimeException() {

    assertThrows(
        RuntimeException.class,
        () -> new PriceAgg(
            new PriceId(UUID.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b")),
            2L,
            simpleDateFormat.parse("2020-06-14-00.00.00"),
            simpleDateFormat.parse("2020-06-13-00.00.00"),
            35455L,
            new Priority(0),
            new PositiveMonetaryAmount(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(35.40).create())
        )
    );
  }

  @Test
  @DisplayName(
      "When price amount has more than 2 digits then the priority must be greater than 10, if not then return "
          + "InvalidPriceWithPriorityException")
  public void test_3() {

    Assertions.assertThrows(InvalidPriceWithPriorityException.class,
        () -> new PriceAgg(
            new PriceId(UUID.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b")),
            2L,
            simpleDateFormat.parse("2020-06-14-00.00.00"),
            simpleDateFormat.parse("2020-12-31-23.59.59"),
            35455L,
            new Priority(10),
            new PositiveMonetaryAmount(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(150.40).create())
        )
    );
  }
}