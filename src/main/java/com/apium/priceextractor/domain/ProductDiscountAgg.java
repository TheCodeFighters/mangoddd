package com.apium.priceextractor.domain;

import java.util.UUID;

import org.javamoney.moneta.function.MonetaryOperators;

public record ProductDiscountAgg(ProductDiscountId id, ProductId productId, DiscountPercentage discountPercentage) {

  public PositiveMonetaryAmount applyDiscount(PositiveMonetaryAmount positiveMonetaryAmount) {
    return new PositiveMonetaryAmount(
        positiveMonetaryAmount.value().subtract(
            getDiscountAmount(positiveMonetaryAmount).value()
        )
    );
  }

  private PositiveMonetaryAmount getDiscountAmount(PositiveMonetaryAmount positiveMonetaryAmount) {
    return new PositiveMonetaryAmount(
        positiveMonetaryAmount.value().with(
            MonetaryOperators.percent(
                discountPercentage.percentage().value())
        )
    );
  }
}
