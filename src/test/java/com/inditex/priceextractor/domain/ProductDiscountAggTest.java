package com.inditex.priceextractor.domain;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductDiscountAggTest {

  private static final String PRODUCT_DISCOUNT_ID = "ed14fd0f-82be-4450-96f7-8fda7ecb0414";

  private static final String PRODUCT_ID = "7f0e9fcb-e004-462b-a42e-1764cc4b3067";

  private static final Double DISCOUNT_PERCENTAGE = 3d;

  @Test
  @DisplayName("when ProductDiscountAgg is created with valid data then should return ProductDiscountAgg")
  public void test_0() {
    ProductDiscountAgg actualProductDiscountAgg = new ProductDiscountAgg(
        ProductDiscountId.fromString(PRODUCT_DISCOUNT_ID),
        ProductId.fromString(PRODUCT_ID),
        new Discount(new PositiveNumber(DISCOUNT_PERCENTAGE))
    );
    Assertions.assertEquals(ProductDiscountId.fromString(PRODUCT_DISCOUNT_ID), actualProductDiscountAgg.getId());
    Assertions.assertEquals(ProductId.fromString(PRODUCT_ID), actualProductDiscountAgg.getProductId());
    Assertions.assertEquals(new Discount(new PositiveNumber(DISCOUNT_PERCENTAGE)), actualProductDiscountAgg.getDiscount());
  }

  @Test
  @DisplayName("")
  public void test_2() {
    ProductDiscountAgg actualProductDiscountAgg = new ProductDiscountAgg(
        ProductDiscountId.fromString(PRODUCT_DISCOUNT_ID),
        ProductId.fromString(PRODUCT_ID),
        new Discount(new PositiveNumber(DISCOUNT_PERCENTAGE))
    );

    //    PositiveMonetaryAmount positiveMonetaryAmount = ;
    //    BrandId givenBrandID = BrandId.fromString("daa9023c-92b7-45fb-a033-dfd5e8193ec5");
    //
    //    actualProductDiscountAgg.applyDiscount()

  }

}