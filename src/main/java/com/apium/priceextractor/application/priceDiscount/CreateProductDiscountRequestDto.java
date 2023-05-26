package com.apium.priceextractor.application.priceDiscount;

import jakarta.validation.constraints.NotNull;

public record CreateProductDiscountRequestDto(
    @NotNull String productId,
    @NotNull String brandId
) {

}
