package com.apium.priceextractor.application.price;

import jakarta.validation.constraints.NotNull;

public record CreatePriceRequestDto(
    @NotNull String applicationDate,
    @NotNull String productId,
    @NotNull String brandId
) {

}
