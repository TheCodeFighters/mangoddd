package com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository;

import java.util.Date;
import java.util.Optional;

import com.inditex.priceextractor.infrastructure.persistence.springdata.PriceEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceEntitySpringDataRepository extends CrudRepository<PriceEntity, Long> {

  Optional<PriceEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
      long productId,
      long brandId,
      @NonNull Date applicationDateAgainstStartDate,
      @NonNull Date applicationDateAgainstEndDate
  );
}