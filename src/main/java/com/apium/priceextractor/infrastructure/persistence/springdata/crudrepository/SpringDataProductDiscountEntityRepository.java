package com.apium.priceextractor.infrastructure.persistence.springdata.crudrepository;

import java.util.Optional;
import java.util.UUID;

import com.apium.priceextractor.infrastructure.persistence.springdata.ProductDiscountEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProductDiscountEntityRepository extends CrudRepository<ProductDiscountEntity, UUID> {

  Optional<ProductDiscountEntity> findByProductId(
      @NonNull UUID productId
  );
}