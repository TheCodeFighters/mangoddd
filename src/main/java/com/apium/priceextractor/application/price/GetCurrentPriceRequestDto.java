package com.apium.priceextractor.application.price;


import jakarta.validation.constraints.NotNull;

public record GetCurrentPriceRequestDto(
    @NotNull String applicationDate,
    @NotNull String productId,
    @NotNull String brandId
) {
}
