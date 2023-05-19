package com.inditex.priceextractor.domain;

import com.inditex.priceextractor.infrastructure.format.date.SimpleDateFormatConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceAggregateTest {

    SimpleDateFormat simpleDateFormat;

    @BeforeEach
    public void setup() {
        this.simpleDateFormat = new SimpleDateFormatConfig().simpleDateFormat();
    }

    @Test
    public void givenValidData_thenShouldWork() throws ParseException {
        Long givenPriceId = 1L;
        Long givenBrandId= 2L;
        Date givenStartDate = this.simpleDateFormat.parse("2020-06-14-00.00.00");
        Date givenEndDate = this.simpleDateFormat.parse("2020-12-31-23.59.59");
        Long givenProductId = 35455L;
        Integer givenPriority = 0;
        Double givenPrice = 35.40;
        Currency givenCurrency = Currency.getInstance("EUR");

        PriceAggregate priceAggregate = new PriceAggregate(
            givenPriceId,
            givenBrandId,
            givenStartDate,
            givenEndDate,
            givenProductId,
            givenPriority,
            givenPrice,
            givenCurrency
        );

        Assertions.assertEquals(priceAggregate.getId(), givenPriceId);
        Assertions.assertEquals(priceAggregate.getBrandId(), givenBrandId);
        Assertions.assertEquals(priceAggregate.getStartDate(), givenStartDate);
        Assertions.assertEquals(priceAggregate.getEndDate(), givenEndDate);
        Assertions.assertEquals(priceAggregate.getProductId(), givenProductId);
        Assertions.assertEquals(priceAggregate.getPriority(), givenPriority);
        Assertions.assertEquals(priceAggregate.getPrice(), givenPrice);
        Assertions.assertEquals(priceAggregate.getCurr(), givenCurrency);

    }

    @Test
    public void givenEndDateNewerThanStartDate_thenThrowRunTimeException() {

        assertThrows(
                RuntimeException.class,
            () -> new PriceAggregate(
                1,
                2,
                this.simpleDateFormat.parse("2020-06-14-00.00.00"),
                this.simpleDateFormat.parse("2020-06-13-00.00.00"),
                35455,
                0,
                35.40,
                Currency.getInstance("EUR")
            )
        );
    }
}
