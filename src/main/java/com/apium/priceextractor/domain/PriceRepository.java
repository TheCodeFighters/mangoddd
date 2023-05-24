package com.apium.priceextractor.domain;

import org.springframework.lang.NonNull;

public interface PriceRepository {

  PriceAgg findOrFailRate(
      @NonNull ProductId productId,
      @NonNull BrandId brandId,
      @NonNull Date date
  );
}
