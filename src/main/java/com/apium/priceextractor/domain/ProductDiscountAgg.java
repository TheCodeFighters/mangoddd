package com.apium.priceextractor.domain;

import com.apium.priceextractor.domain.dto.ProductDiscountDto;
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

  public ProductDiscountDto toDto() {
    return new ProductDiscountDto(
        id.toString(),
        productId.toString(),
        discountPercentage.toDouble()
    );
  }
}
