package com.inditex.priceextractor.domain;

import java.util.Date;
import java.util.Optional;

import org.springframework.lang.NonNull;

public interface PriceRepository {

  Optional<PriceAgg> findRate(
      @NonNull ProductId productId,
      @NonNull BrandId brandId,
      @NonNull Date date
  );

  void Save(PriceAgg priceAgg);
}
