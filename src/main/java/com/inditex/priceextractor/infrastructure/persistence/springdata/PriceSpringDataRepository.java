package com.inditex.priceextractor.infrastructure.persistence.springdata;

import java.util.Date;
import java.util.Optional;

import com.inditex.priceextractor.domain.Price;
import com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository.PriceEntitySpringDataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component("SpringData")
public class PriceSpringDataRepository implements com.inditex.priceextractor.domain.PriceRepository {

  private final PriceEntitySpringDataRepository priceEntitySpringDataRepository;

  public PriceSpringDataRepository(PriceEntitySpringDataRepository priceEntitySpringDataRepository) {
    this.priceEntitySpringDataRepository = priceEntitySpringDataRepository;
  }

  public Optional<Price> findRate(
      long productId,
      long brandId,
      @NonNull Date date
  ){
    return priceEntitySpringDataRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        productId,
        brandId,
        date,
        date
    ).map(PriceEntity::toPrice);
  }

}
