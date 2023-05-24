package com.apium.priceextractor.application.priceservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.apium.priceextractor.domain.PriceAgg;

import jakarta.validation.constraints.NotNull;

import java.text.SimpleDateFormat;

public record PriceDto(
    @JsonProperty("price_list") String priceId,
    @JsonProperty("product_id") String productId,
    @JsonProperty("brand_id") String brandId,
    @NotNull @JsonProperty("start_date") String startDate,
    @NotNull @JsonProperty("end_date") String endDate,
    @JsonProperty("price") Double price
) {

  public static PriceDto fromPrice(SimpleDateFormat simpleDateFormat, PriceAgg priceAggregate) {
    return new PriceDto(
        priceAggregate.getId().toString(),
        priceAggregate.getProductId().id().toString(),
        priceAggregate.getBrandId().id().toString(),
        simpleDateFormat.format(priceAggregate.getStartDate()),
        simpleDateFormat.format(priceAggregate.getEndDate()),
        priceAggregate.getPositiveMonetaryAmount().value().getNumber().doubleValue()
    );
  }

}
