package com.inditex.priceextractor.infrastructure.persistence.jdbctemplate;

import static org.junit.Assert.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.inditex.priceextractor.PriceExtractorApplication;
import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PositiveMonetaryAmount;
import com.inditex.priceextractor.domain.PriceAgg;
import com.inditex.priceextractor.domain.PriceId;
import com.inditex.priceextractor.domain.Priority;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.domain.exception.DomainEntityNotFoundException;

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
class JdbcTemplatePriceRepositoryIT {

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
  private JdbcTemplatePriceRepository jdbcTemplatePriceRepository;

  @Test
  @DisplayName("getting a existent PriceAgg by its Id should return an PriceAgg")
  public void test_0() throws ParseException {
    ProductId givenProductId = ProductId.fromString("7f0e9fcb-e004-462b-a42e-1764cc4b3067");
    BrandId givenBrandId = BrandId.fromString("5ecffb3d-3472-4420-91cd-80ecd83981d8");
    Date givenDate = simpleDateFormat.parse("2020-06-14-10.00.00");
    PriceAgg actualPriceAgg = jdbcTemplatePriceRepository.findOrFailRate(givenProductId, givenBrandId, givenDate);
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
  @DisplayName("getting a non existent PriceAgg then should thrown DomainEntityNotFoundException")
  public void test_1() throws ParseException {
    ProductId givenNonExistentProductId = ProductId.fromString("11ff7f41-50a4-47f0-a27b-1987ce29bbf8");
    BrandId givenBrandId = BrandId.fromString("5ecffb3d-3472-4420-91cd-80ecd83981d8");
    Date givenDate = simpleDateFormat.parse("2020-06-14-10.00.00");
    assertThrows(DomainEntityNotFoundException.class,
        () -> jdbcTemplatePriceRepository.findOrFailRate(givenNonExistentProductId, givenBrandId, givenDate));
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
