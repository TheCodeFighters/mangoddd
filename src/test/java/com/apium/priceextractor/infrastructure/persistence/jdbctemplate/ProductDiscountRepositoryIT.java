package com.apium.priceextractor.infrastructure.persistence.jdbctemplate;

import com.apium.priceextractor.PriceExtractorApplication;
import com.apium.priceextractor.domain.DiscountPercentage;
import com.apium.priceextractor.domain.PositiveNumber;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountId;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.domain.ProductId;
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
class ProductDiscountRepositoryIT {

  public static final ProductDiscountId PRODUCT_DISCOUNT_ID = ProductDiscountId.fromString("ed14fd0f-82be-4450-96f7-8fda7ecb0414");

  public static final ProductId PRODUCT_ID = ProductId.fromString("5d2102e9-6311-4c1b-a34d-326e7df8a235");

  public static final DiscountPercentage DISCOUNT_PERCENTAGE = new DiscountPercentage(new PositiveNumber(3d));

  @Autowired
  private ProductDiscountRepository productDiscountRepository;

  @Test
  @DisplayName("getting a existent ProductDiscountAgg by ProductId should return an ProductDiscountAgg")
  public void test_0() {
    ProductDiscountAgg actualProductDiscountAgg = productDiscountRepository.findOrDefaultByProductId(PRODUCT_ID);
    ProductDiscountAgg expectedProductDiscountAgg = createProductDiscountAgg();
    Assertions.assertEquals(expectedProductDiscountAgg, actualProductDiscountAgg);
  }

  @Test
  @DisplayName("getting a non existent ProductDiscountAgg then should return ProductDiscountAgg with givenProductID and discount 0")
  public void test_1() {
    ProductId givenNonExistentProductId = ProductId.fromString("63db9838-69f6-4b5e-bad7-632603ad7aaf");

    ProductDiscountAgg expectedProductDiscountAgg =
        new ProductDiscountAgg(null, givenNonExistentProductId, new DiscountPercentage(new PositiveNumber(0d)));

    ProductDiscountAgg actualProductDiscountAgg = productDiscountRepository.findOrDefaultByProductId(givenNonExistentProductId);
    Assertions.assertEquals(expectedProductDiscountAgg, actualProductDiscountAgg);
  }

  private ProductDiscountAgg createProductDiscountAgg() {
    return new ProductDiscountAgg(
        PRODUCT_DISCOUNT_ID,
        PRODUCT_ID,
        DISCOUNT_PERCENTAGE
    );
  }

}
