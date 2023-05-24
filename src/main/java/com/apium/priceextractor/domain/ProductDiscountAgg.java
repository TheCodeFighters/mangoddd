package com.apium.priceextractor.domain;

import java.util.UUID;

import org.javamoney.moneta.function.MonetaryOperators;

public class ProductDiscountAgg {

  public static final UUID BRAND_CHINA = UUID.fromString("ed14fd0f-82be-4450-96f7-8fda7ecb0414");

  private final ProductDiscountId id;

  private final ProductId productId;

  private final DiscountPercentage discountPercentage;

  public ProductDiscountAgg(ProductDiscountId id, ProductId productId, DiscountPercentage discountPercentage) {
    this.id = id;
    this.productId = productId;
    this.discountPercentage = discountPercentage;
  }

  public PositiveMonetaryAmount applyDiscount(PositiveMonetaryAmount positiveMonetaryAmount, BrandId brandId) {
    return new PositiveMonetaryAmount(
        positiveMonetaryAmount.value().subtract(
            getDiscountAmount(positiveMonetaryAmount, brandId).value()
        )
    );
  }

  public PositiveMonetaryAmount getDiscountAmount(PositiveMonetaryAmount positiveMonetaryAmount, BrandId brandId) {
    if (isDiscountApplicable(brandId)) {
      return new PositiveMonetaryAmount(
          positiveMonetaryAmount.value().with(
              MonetaryOperators.percent(
                  discountPercentage.percentage().value())
          )
      );
    }
    return PositiveMonetaryAmount.fromDoubleAndCurrency(0d, positiveMonetaryAmount.value().getCurrency().toString());
  }

  private boolean isDiscountApplicable(BrandId brandId) {
    return !brandId.id().equals(BRAND_CHINA);
  }

  public ProductDiscountId getId() {
    return id;
  }

  public ProductId getProductId() {
    return productId;
  }

  public DiscountPercentage getDiscountPercentage() {
    return discountPercentage;
  }
}
