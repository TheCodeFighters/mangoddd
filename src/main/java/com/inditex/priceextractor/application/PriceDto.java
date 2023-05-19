package com.inditex.priceextractor.application;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.inditex.priceextractor.domain.PriceAggregate;

import jakarta.validation.constraints.NotNull;

import java.text.SimpleDateFormat;


public record PriceDto(
        @JsonProperty("price_list") long priceId,
        @JsonProperty("product_id") long productId,
        @JsonProperty("brand_id") long brandId,
        @NotNull @JsonProperty("start_date") String startDate,
        @NotNull@JsonProperty("end_date") String endDate,
        @JsonProperty("price") double price
) {

    public static PriceDto fromPrice(SimpleDateFormat simpleDateFormat, PriceAggregate priceAggregate) {

        return new PriceDto(
            priceAggregate.getId(),
            priceAggregate.getProductId(),
            priceAggregate.getBrandId(),
            simpleDateFormat.format(priceAggregate.getStartDate()),
            simpleDateFormat.format(priceAggregate.getEndDate()),
            priceAggregate.getPrice()
        );
    }

}
