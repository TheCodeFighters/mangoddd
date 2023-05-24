package com.apium.priceextractor.infrastructure.erp;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.ProductDiscountERPRepository;
import com.apium.priceextractor.domain.ProductDiscountAgg;

public class SapProductDiscountRepository implements ProductDiscountERPRepository {

  public ProductDiscountAgg findAllOrFailByBrandId(BrandId brandId) {
    //TODO not implemented yet
    return null;
  }
}
