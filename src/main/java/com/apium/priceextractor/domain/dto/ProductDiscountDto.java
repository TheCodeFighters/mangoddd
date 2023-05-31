package com.apium.priceextractor.domain.dto;

public record ProductDiscountDto(
    String productDiscountId,
    String productId,
    Double discountPercentage
) {

}
