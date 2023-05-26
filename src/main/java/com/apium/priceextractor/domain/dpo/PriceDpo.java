package com.apium.priceextractor.domain.dpo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record PriceDpo(
    @JsonProperty("price_list") String priceId,
    @JsonProperty("product_id") String productId,
    @JsonProperty("brand_id") String brandId,
    @NotNull @JsonProperty("start_date") String startDate,
    @NotNull @JsonProperty("end_date") String endDate,
    @JsonProperty("price") Double price
) {

}
