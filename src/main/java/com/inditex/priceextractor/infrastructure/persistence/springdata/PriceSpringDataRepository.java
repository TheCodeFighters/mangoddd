package com.inditex.priceextractor.infrastructure.persistence.springdata;

import java.util.Date;
import java.util.Optional;

import com.inditex.priceextractor.domain.PriceAggregate;
import com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository.PriceEntitySpringDataRepository;

import org.springframework.lang.NonNull;

public class PriceSpringDataRepository implements com.inditex.priceextractor.domain.PriceRepository {

  private final PriceEntitySpringDataRepository priceEntitySpringDataRepository;

  public PriceSpringDataRepository(PriceEntitySpringDataRepository priceEntitySpringDataRepository) {
    this.priceEntitySpringDataRepository = priceEntitySpringDataRepository;
  }

  public Optional<PriceAggregate> findRate(
      long productId,
      long brandId,
      @NonNull Date date
  ) {
    return priceEntitySpringDataRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        productId,
        brandId,
        date,
        date
    ).map(PriceEntity::toPrice);
  }

}
