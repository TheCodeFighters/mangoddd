package com.apium.priceextractor.infrastructure.persistence.springdata.crudrepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.apium.priceextractor.infrastructure.persistence.entity.PriceEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPriceEntityRepository extends CrudRepository<PriceEntity, UUID> {

  Optional<PriceEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
      @NonNull UUID productId,
      @NonNull UUID brandId,
      @NonNull Date applicationDateAgainstStartDate,
      @NonNull Date applicationDateAgainstEndDate
  );
}