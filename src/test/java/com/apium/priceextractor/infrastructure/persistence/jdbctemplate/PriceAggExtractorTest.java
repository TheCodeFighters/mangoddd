package com.apium.priceextractor.infrastructure.persistence.jdbctemplate;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.PositiveMonetaryAmount;
import com.apium.priceextractor.domain.PriceAgg;
import com.apium.priceextractor.domain.PriceId;
import com.apium.priceextractor.domain.Priority;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.domain.exception.DomainEntityNotFoundException;
import com.apium.priceextractor.infrastructure.format.date.SimpleDateFormatConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceAggExtractorTest {

  public static final UUID PRICE_ID = UUID.randomUUID();

  public static final UUID BRAND_ID = UUID.randomUUID();

  public static final String START_DATE = "2020-06-14-00.00.00";

  public static final String END_DATE = "2020-12-31-23.59.59";

  public static final UUID PRODUCT_ID = UUID.randomUUID();

  public static final int PRIORITY = 0;

  public static final double PRICE = 35.50;

  public static final String CURRENCY = "EUR";

  public static final UUID PRODUCT_DISCOUNT_ID = UUID.randomUUID();

  private SimpleDateFormat simpleDateFormat;

  private PriceAggExtractor priceAggExtractor;

  @BeforeEach
  public void setup() {
    priceAggExtractor = new PriceAggExtractor();
    simpleDateFormat = new SimpleDateFormatConfig().simpleDateFormat();
  }

  @Test
  @DisplayName("when valid ResultSet has been provided the return a PriceAgg")
  public void test_0() throws SQLException, ParseException {
    ResultSet resultSetMock = createResultSetMock();
    PriceAgg actualPriceAgg = priceAggExtractor.extractPriceAgg(resultSetMock);
    PriceAgg expectedPriceAgg = createPriceAgg();
    Assertions.assertEquals(expectedPriceAgg.getId(), actualPriceAgg.getId());
    Assertions.assertEquals(expectedPriceAgg.getBrandId(), actualPriceAgg.getBrandId());
    Assertions.assertEquals(expectedPriceAgg.getStartDate(), actualPriceAgg.getStartDate());
    Assertions.assertEquals(expectedPriceAgg.getEndDate(), actualPriceAgg.getEndDate());
    Assertions.assertEquals(expectedPriceAgg.getProductId(), actualPriceAgg.getProductId());
    Assertions.assertEquals(expectedPriceAgg.getPriority(), actualPriceAgg.getPriority());
    Assertions.assertEquals(expectedPriceAgg.getPositiveMonetaryAmount(), actualPriceAgg.getPositiveMonetaryAmount());
  }

  @Test
  @DisplayName("when ResultSet Access to a invalid column then throw a DomainEntityNotFoundException")
  public void test_1() throws SQLException {
    ResultSet resultSetMock = mock(ResultSet.class);
    when(resultSetMock.getString(any())).thenThrow(new SQLException());
    assertThrows(DomainEntityNotFoundException.class, () -> priceAggExtractor.extractPriceAgg(resultSetMock));
  }

  private ResultSet createResultSetMock() throws SQLException, ParseException {
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getString("id")).thenReturn(PRICE_ID.toString());
    when(resultSet.getString("brand_id")).thenReturn(BRAND_ID.toString());
    when(resultSet.getTimestamp("start_date")).thenReturn(new Timestamp(simpleDateFormat.parse(START_DATE).getTime()));
    when(resultSet.getTimestamp("end_date")).thenReturn(new Timestamp(simpleDateFormat.parse(END_DATE).getTime()));
    when(resultSet.getString("product_id")).thenReturn(PRODUCT_ID.toString());
    when(resultSet.getInt("priority")).thenReturn(PRIORITY);
    when(resultSet.getDouble("price")).thenReturn(PRICE);
    when(resultSet.getString("curr")).thenReturn(CURRENCY);
    when(resultSet.getString("product_discount_id")).thenReturn(PRODUCT_DISCOUNT_ID.toString());
    return resultSet;
  }

  private PriceAgg createPriceAgg() throws ParseException {
    return new PriceAgg(
        new PriceId(PRICE_ID),
        new BrandId(BRAND_ID),
        simpleDateFormat.parse(START_DATE),
        simpleDateFormat.parse(END_DATE),
        new ProductId(PRODUCT_ID),
        new Priority(PRIORITY),
        PositiveMonetaryAmount.fromDoubleAndCurrency(PRICE, CURRENCY)
    );
  }

}