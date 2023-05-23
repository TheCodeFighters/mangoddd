package com.inditex.priceextractor.infrastructure.persistence.springdata;

import java.util.Date;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PriceAgg;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.domain.exception.DomainEntityNotFoundException;
import com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository.SpringDataPriceEntityRepository;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SpringDataPriceRepository implements com.inditex.priceextractor.domain.PriceRepository {

  private final SpringDataPriceEntityRepository springDataPriceEntityRepository;

  public SpringDataPriceRepository(SpringDataPriceEntityRepository springDataPriceEntityRepository) {
    this.springDataPriceEntityRepository = springDataPriceEntityRepository;
  }

  public PriceAgg findOrfailRate(
      @NonNull ProductId productId,
      @NonNull BrandId brandId,
      @NonNull Date date
  ) {
    return springDataPriceEntityRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        productId.id(),
        brandId.id(),
        date,
        date
    ).map(PriceEntity::toPrice).orElseThrow(() -> new DomainEntityNotFoundException("PriceAgg not found"));
  }

}
