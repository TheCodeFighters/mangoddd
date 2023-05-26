package com.apium.priceextractor.domain.dpo;

public record ProductDiscountDpo(
    String id,
    String productId,
    Double discountPercentage
) {

}
