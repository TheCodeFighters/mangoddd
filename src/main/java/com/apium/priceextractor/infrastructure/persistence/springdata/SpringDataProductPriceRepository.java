package com.apium.priceextractor.infrastructure.persistence.springdata;

import com.apium.priceextractor.domain.DiscountPercentage;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.infrastructure.persistence.springdata.crudrepository.SpringDataProductDiscountEntityRepository;
import jakarta.validation.constraints.NotNull;

public class SpringDataProductPriceRepository implements ProductDiscountRepository {

  private final SpringDataProductDiscountEntityRepository springDataProductDiscountEntityRepository;

  public SpringDataProductPriceRepository(SpringDataProductDiscountEntityRepository springDataProductDiscountEntityRepository) {
    this.springDataProductDiscountEntityRepository = springDataProductDiscountEntityRepository;
  }

  @Override
  public ProductDiscountAgg findOrDefaultByProductId(ProductId productId) {
    return springDataProductDiscountEntityRepository.findByProductId(productId.id()).map(ProductDiscountEntity::toProductDiscountAgg)
        .orElse(new ProductDiscountAgg(null, productId, DiscountPercentage.fromDouble(0d)));
  }

  @Override
  public void save(@NotNull ProductDiscountAgg productDiscountAgg) {
    //TODO not implemented yet
  }
}
