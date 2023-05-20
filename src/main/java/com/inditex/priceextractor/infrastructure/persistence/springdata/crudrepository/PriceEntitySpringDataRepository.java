package com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository;

import java.util.Date;
import java.util.Optional;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.infrastructure.persistence.springdata.PriceEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceEntitySpringDataRepository extends CrudRepository<PriceEntity, Long> {

  Optional<PriceEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
      @NonNull ProductId productId,
      @NonNull BrandId brandId,
      @NonNull Date applicationDateAgainstStartDate,
      @NonNull Date applicationDateAgainstEndDate
  );
}