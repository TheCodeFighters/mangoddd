package com.inditex.priceextractor.infrastructure.persistence.springdata;

import com.inditex.priceextractor.domain.ProductDiscountAgg;
import com.inditex.priceextractor.domain.ProductDiscountId;
import com.inditex.priceextractor.domain.ProductDiscountRepository;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.domain.exception.DomainEntityNotFoundException;
import com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository.SpringDataProductDiscountEntityRepository;

public class SpringDataProductPriceRepository implements ProductDiscountRepository {

  private final SpringDataProductDiscountEntityRepository springDataProductDiscountEntityRepository;

  public SpringDataProductPriceRepository(SpringDataProductDiscountEntityRepository springDataProductDiscountEntityRepository) {
    this.springDataProductDiscountEntityRepository = springDataProductDiscountEntityRepository;
  }

  @Override
  public ProductDiscountAgg findOrFailByProductId(ProductId productId) {
    return springDataProductDiscountEntityRepository.findByProductId(productId.id()).map(ProductDiscountEntity::toProductDiscountAgg)
        .orElseThrow(() -> new DomainEntityNotFoundException("ProductDiscountAgg not found"));
  }
}
