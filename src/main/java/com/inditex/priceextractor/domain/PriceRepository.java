package com.inditex.priceextractor.domain;

import java.util.Date;
import java.util.Optional;

import org.springframework.lang.NonNull;

public interface PriceRepository {

  Optional<PriceAggregate> findRate(
      long productId,
      long brandId,
      @NonNull Date date
  );
}
