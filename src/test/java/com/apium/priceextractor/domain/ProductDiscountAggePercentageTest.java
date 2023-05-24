package com.apium.priceextractor.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductDiscountAggePercentageTest {

  private static final String PRODUCT_DISCOUNT_ID = "ed14fd0f-82be-4450-96f7-8fda7ecb0414";

  private static final String PRODUCT_ID = "7f0e9fcb-e004-462b-a42e-1764cc4b3067";

  private static final Double DISCOUNT_PERCENTAGE = 3d;

  @Test
  @DisplayName("when ProductDiscountAgg is created with valid data then should return ProductDiscountAgg")
  public void test_0() {
    ProductDiscountAgg actualProductDiscountAgg = new ProductDiscountAgg(
        ProductDiscountId.fromString(PRODUCT_DISCOUNT_ID),
        ProductId.fromString(PRODUCT_ID),
        new DiscountPercentage(new PositiveNumber(DISCOUNT_PERCENTAGE))
    );
    Assertions.assertEquals(ProductDiscountId.fromString(PRODUCT_DISCOUNT_ID), actualProductDiscountAgg.id());
    Assertions.assertEquals(ProductId.fromString(PRODUCT_ID), actualProductDiscountAgg.productId());
    Assertions.assertEquals(new DiscountPercentage(new PositiveNumber(DISCOUNT_PERCENTAGE)),
        actualProductDiscountAgg.discountPercentage());
  }

  @Test
  @DisplayName("when discount is applied over a PositiveMonetaryAmount then should return PositiveMonetaryAmount with discounted applied")
  public void test_2() {
    ProductDiscountAgg givenProductDiscountAgg = new ProductDiscountAgg(
        ProductDiscountId.fromString(PRODUCT_DISCOUNT_ID),
        ProductId.fromString(PRODUCT_ID),
        new DiscountPercentage(new PositiveNumber(DISCOUNT_PERCENTAGE))
    );

    PositiveMonetaryAmount positiveMonetaryAmount = PositiveMonetaryAmount.fromDoubleAndCurrency(150d, "EUR");
    BrandId givenBrandIDWhichIsNotChina = BrandId.fromString("daa9023c-92b7-45fb-a033-dfd5e8193ec5");

    PositiveMonetaryAmount actualPositiveMonetaryAmount = givenProductDiscountAgg.applyDiscount(
        positiveMonetaryAmount,
        givenBrandIDWhichIsNotChina
    );
    PositiveMonetaryAmount expectedPositiveMonetaryAmount = PositiveMonetaryAmount.fromDoubleAndCurrency(145.5d, "EUR");
    Assertions.assertEquals(expectedPositiveMonetaryAmount, actualPositiveMonetaryAmount);
  }

  @Test
  @DisplayName(
      "when discount is applied over a PositiveMonetaryAmount for China Brand then should return PositiveMonetaryAmount with discounted "
          + "not applied")
  public void test_3() {
    ProductDiscountAgg givenProductDiscountAgg = new ProductDiscountAgg(
        ProductDiscountId.fromString(PRODUCT_DISCOUNT_ID),
        ProductId.fromString(PRODUCT_ID),
        new DiscountPercentage(new PositiveNumber(DISCOUNT_PERCENTAGE))
    );

    PositiveMonetaryAmount positiveMonetaryAmount = PositiveMonetaryAmount.fromDoubleAndCurrency(150d, "EUR");
    BrandId givenBrandIDWhichIsNotChina = new BrandId(ProductDiscountAgg.BRAND_CHINA);

    PositiveMonetaryAmount actualPositiveMonetaryAmount = givenProductDiscountAgg.applyDiscount(
        positiveMonetaryAmount,
        givenBrandIDWhichIsNotChina
    );
    PositiveMonetaryAmount expectedPositiveMonetaryAmount = PositiveMonetaryAmount.fromDoubleAndCurrency(150d, "EUR");
    Assertions.assertEquals(expectedPositiveMonetaryAmount, actualPositiveMonetaryAmount);
  }

}