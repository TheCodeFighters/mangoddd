package com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PriceId;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.infrastructure.persistence.springdata.PriceEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceEntitySpringDataRepository extends CrudRepository<PriceEntity, UUID> {

  Optional<PriceEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
      @NonNull UUID productId,
      @NonNull UUID brandId,
      @NonNull Date applicationDateAgainstStartDate,
      @NonNull Date applicationDateAgainstEndDate
  );
}