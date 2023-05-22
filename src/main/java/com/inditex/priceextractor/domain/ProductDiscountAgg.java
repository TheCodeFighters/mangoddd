package com.inditex.priceextractor.domain;

import java.util.UUID;

import javax.money.Monetary;
import org.javamoney.moneta.function.MonetaryOperators;

public class ProductDiscountAgg {

  public static final UUID BRAND_CHINA = UUID.fromString("c3f8e657-c76b-46f8-9c2c-fb14fa5113c8");

  private ProductDiscountId id;

  private ProductId productId;

  private Discount discount;

  public ProductDiscountAgg(ProductDiscountId id) {
    this.id = id;
  }

  public PositiveMonetaryAmount applyDiscount(PositiveMonetaryAmount positiveMonetaryAmount, BrandId brandId) {
    if (isDiscountApplicable(brandId)) {
      try {
        return new PositiveMonetaryAmount(positiveMonetaryAmount.value().with(MonetaryOperators.percent(discount.percentage().value())));
      } catch (PositiveMonetaryAmountException e) {
        return new PositiveMonetaryAmount(
            Monetary.getDefaultAmountFactory().setCurrency(positiveMonetaryAmount.value().getCurrency()).setNumber(0).create()
        );
      }
    }
    return positiveMonetaryAmount;
  }

  private boolean isDiscountApplicable(BrandId brandId) {
    return brandId.id().equals(BRAND_CHINA);
  }

  public ProductDiscountId getId() {
    return id;
  }

  public ProductId getProductId() {
    return productId;
  }

  public Discount getDiscount() {
    return discount;
  }
}
