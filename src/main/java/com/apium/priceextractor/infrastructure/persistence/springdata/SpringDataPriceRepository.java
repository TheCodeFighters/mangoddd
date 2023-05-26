package com.apium.priceextractor.infrastructure.persistence.springdata;


import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.Date;
import com.apium.priceextractor.domain.PriceAgg;
import com.apium.priceextractor.domain.PriceRepository;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.domain.exception.DomainEntityNotFoundException;
import com.apium.priceextractor.infrastructure.persistence.entity.PriceEntity;
import com.apium.priceextractor.infrastructure.persistence.springdata.crudrepository.SpringDataPriceEntityRepository;
import org.springframework.lang.NonNull;

public class SpringDataPriceRepository implements PriceRepository {

  private final SpringDataPriceEntityRepository springDataPriceEntityRepository;

  public SpringDataPriceRepository(SpringDataPriceEntityRepository springDataPriceEntityRepository) {
    this.springDataPriceEntityRepository = springDataPriceEntityRepository;
  }

  public PriceAgg findOrFailRate(
      @NonNull ProductId productId,
      @NonNull BrandId brandId,
      @NonNull Date date
  ) {
    return springDataPriceEntityRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        productId.id(),
        brandId.id(),
        date.date(),
        date.date()
    ).map(PriceEntity::toPrice).orElseThrow(() -> new DomainEntityNotFoundException("PriceAgg not found"));
  }

}
