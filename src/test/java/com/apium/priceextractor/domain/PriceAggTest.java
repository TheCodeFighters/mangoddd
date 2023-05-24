package com.apium.priceextractor.domain;

import java.util.UUID;

import com.apium.priceextractor.domain.exception.InvalidPriceWithPriorityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PriceAggTest {

  @Test
  public void givenValidData_thenShouldWork() {
    PriceId givenPriceId = new PriceId(UUID.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b"));
    BrandId givenBrandId = new BrandId(UUID.fromString("5ecffb3d-3472-4420-91cd-80ecd83981d8"));
    Date givenStartDate = Date.fromString("2020-06-14-00.00.00");
    Date givenEndDate = Date.fromString("2020-12-31-23.59.59");
    DateRange givenDateRange = new DateRange(givenStartDate, givenEndDate);
    ProductId givenProductId = new ProductId(UUID.fromString("7f0e9fcb-e004-462b-a42e-1764cc4b3067"));
    Priority givenPriority = new Priority(0);
    PositiveMonetaryAmount givenpositiveMonetaryAmount = PositiveMonetaryAmount.fromDoubleAndCurrency(35.40, "EUR");

    PriceAgg priceAggregate = new PriceAgg(
        givenPriceId,
        givenBrandId,
        givenDateRange,
        givenProductId,
        givenPriority,
        givenpositiveMonetaryAmount
    );

    Assertions.assertEquals(givenPriceId, priceAggregate.id());
    Assertions.assertEquals(givenBrandId, priceAggregate.brandId());
    Assertions.assertEquals(givenDateRange, priceAggregate.dateRange());
    Assertions.assertEquals(givenProductId, priceAggregate.productId());
    Assertions.assertEquals(givenPriority, priceAggregate.priority());
    Assertions.assertEquals(givenpositiveMonetaryAmount, priceAggregate.positiveMonetaryAmount());
  }

  @Test
  @DisplayName(
      "When price amount has more than 2 digits then the priority must be greater than 10, if not then return "
          + "InvalidPriceWithPriorityException")
  public void test_3() {

    Assertions.assertThrows(InvalidPriceWithPriorityException.class,
        () -> new PriceAgg(
            PriceId.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b"),
            BrandId.fromString("5ecffb3d-3472-4420-91cd-80ecd83981d8"),
            new DateRange(Date.fromString("2020-06-14-00.00.00"), Date.fromString("2020-12-31-23.59.59")),
            ProductId.fromString("7f0e9fcb-e004-462b-a42e-1764cc4b3067"),
            new Priority(10),
            PositiveMonetaryAmount.fromDoubleAndCurrency(150.40, "EUR")
        )
    );
  }
}
