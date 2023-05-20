package com.inditex.priceextractor.infrastructure.persistence.springdata;

import java.util.Date;
import java.util.Optional;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PriceAgg;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository.PriceEntitySpringDataRepository;

import org.springframework.lang.NonNull;

public class PriceSpringDataRepository implements com.inditex.priceextractor.domain.PriceRepository {

  private final PriceEntitySpringDataRepository priceEntitySpringDataRepository;

  public PriceSpringDataRepository(PriceEntitySpringDataRepository priceEntitySpringDataRepository) {
    this.priceEntitySpringDataRepository = priceEntitySpringDataRepository;
  }

  public Optional<PriceAgg> findRate(
      @NonNull ProductId productId,
      @NonNull BrandId brandId,
      @NonNull Date date
  ) {
    return priceEntitySpringDataRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        productId.id(),
        brandId.id(),
        date,
        date
    ).map(PriceEntity::toPrice);
  }

}
