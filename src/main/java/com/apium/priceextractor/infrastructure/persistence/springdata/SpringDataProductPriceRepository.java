package com.apium.priceextractor.infrastructure.persistence.springdata;

import com.apium.priceextractor.domain.DiscountPercentage;
import com.apium.priceextractor.domain.PositiveNumber;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.domain.exception.DomainEntityNotFoundException;
import com.apium.priceextractor.infrastructure.persistence.springdata.crudrepository.SpringDataProductDiscountEntityRepository;

public class SpringDataProductPriceRepository implements ProductDiscountRepository {

  private final SpringDataProductDiscountEntityRepository springDataProductDiscountEntityRepository;

  public SpringDataProductPriceRepository(SpringDataProductDiscountEntityRepository springDataProductDiscountEntityRepository) {
    this.springDataProductDiscountEntityRepository = springDataProductDiscountEntityRepository;
  }

  @Override
  public ProductDiscountAgg findOrDefaultByProductId(ProductId productId) {
    return springDataProductDiscountEntityRepository.findByProductId(productId.id()).map(ProductDiscountEntity::toProductDiscountAgg)
        .orElse(new ProductDiscountAgg(null, productId, new DiscountPercentage(new PositiveNumber(0d))));
  }
}
