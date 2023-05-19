package com.inditex.priceextractor.application;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.inditex.priceextractor.domain.PriceAgg;

import jakarta.validation.constraints.NotNull;

import java.text.SimpleDateFormat;

public record PriceDto(
    @JsonProperty("price_list") String priceId,
    @JsonProperty("product_id") Long productId,
    @JsonProperty("brand_id") Long brandId,
    @NotNull @JsonProperty("start_date") String startDate,
    @NotNull @JsonProperty("end_date") String endDate,
    @JsonProperty("price") Double price
) {

  public static PriceDto fromPrice(SimpleDateFormat simpleDateFormat, PriceAgg priceAggregate) {
    return new PriceDto(
        priceAggregate.getId().toString(),
        priceAggregate.getProductId(),
        priceAggregate.getBrandId(),
        simpleDateFormat.format(priceAggregate.getStartDate()),
        simpleDateFormat.format(priceAggregate.getEndDate()),
        priceAggregate.getPositiveMonetaryAmount().value().getNumber().doubleValue()
    );
  }

}
