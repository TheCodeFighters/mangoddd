package com.apium.priceextractor.infrastructure.erp;

import java.util.Optional;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountERPRepository;
import com.apium.priceextractor.domain.ProductId;

public class OdooProductDiscountRepository implements ProductDiscountERPRepository {

  public Optional<ProductDiscountAgg> findOrFailByProductIdOrBrandId(ProductId productId, BrandId brandId) {
    //TODO not implemented yet
    return Optional.empty();
  }
}
