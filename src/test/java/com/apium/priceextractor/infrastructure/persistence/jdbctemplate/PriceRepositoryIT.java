package com.apium.priceextractor.infrastructure.persistence.jdbctemplate;

import static org.junit.Assert.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import com.apium.priceextractor.PriceExtractorApplication;
import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.Date;
import com.apium.priceextractor.domain.PositiveMonetaryAmount;
import com.apium.priceextractor.domain.PriceAgg;
import com.apium.priceextractor.domain.PriceId;
import com.apium.priceextractor.domain.PriceRepository;
import com.apium.priceextractor.domain.Priority;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.domain.exception.DomainEntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = PriceExtractorApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PriceRepositoryIT {

  public static final UUID PRICE_ID = UUID.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b");

  public static final UUID BRAND_ID = UUID.fromString("5ecffb3d-3472-4420-91cd-80ecd83981d8");

  public static final String START_DATE = "2020-06-14-00.00.00";

  public static final String END_DATE = "2020-12-31-23.59.59";

  public static final UUID PRODUCT_ID = UUID.fromString("7f0e9fcb-e004-462b-a42e-1764cc4b3067");

  public static final int PRIORITY = 0;

  public static final double PRICE = 35.50;

  public static final String CURRENCY = "EUR";

  public static final UUID PRODUCT_DISCOUNT_ID = UUID.fromString("25830ea3-b572-48f6-9804-ad0acb17082c");

  @Autowired
  private SimpleDateFormat simpleDateFormat;

  @Autowired
  private PriceRepository priceRepository;

  //TODO parametrized test para testear spring data y jdbc
  @Test
  @Transactional
  @DisplayName("getting a existent PriceAgg by its Id should return an PriceAgg")
  public void test_0() throws ParseException {
    ProductId givenProductId = ProductId.fromString("7f0e9fcb-e004-462b-a42e-1764cc4b3067");
    BrandId givenBrandId = BrandId.fromString("5ecffb3d-3472-4420-91cd-80ecd83981d8");
    Date givenDate = Date.fromString("2020-06-14-10.00.00");
    PriceAgg actualPriceAgg = priceRepository.findOrFailRate(givenProductId, givenBrandId, givenDate);
    PriceAgg expectedPriceAgg = createPriceAgg();
    Assertions.assertEquals(expectedPriceAgg, actualPriceAgg);
  }

  @Test
  @Transactional
  @DisplayName("getting a non existent PriceAgg then should thrown DomainEntityNotFoundException")
  public void test_1() {
    ProductId givenNonExistentProductId = ProductId.fromString("11ff7f41-50a4-47f0-a27b-1987ce29bbf8");
    BrandId givenBrandId = BrandId.fromString("5ecffb3d-3472-4420-91cd-80ecd83981d8");
    Date givenDate = Date.fromString("2020-06-14-10.00.00");
    assertThrows(DomainEntityNotFoundException.class,
        () -> priceRepository.findOrFailRate(givenNonExistentProductId, givenBrandId, givenDate));
  }

  private PriceAgg createPriceAgg() {
    return new PriceAgg(
        new PriceId(PRICE_ID),
        new BrandId(BRAND_ID),
        Date.fromString(START_DATE),
        Date.fromString(END_DATE),
        new ProductId(PRODUCT_ID),
        new Priority(PRIORITY),
        PositiveMonetaryAmount.fromDoubleAndCurrency(PRICE, CURRENCY)
    );
  }

}
