package com.apium.priceextractor.infrastructure.erp;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountERPRepository;

public class OdooProductDiscountRepository implements ProductDiscountERPRepository {

  public ProductDiscountAgg findAllOrFailByBrandId(BrandId brandId) {
    //TODO not implemented yet
    return null;
  }
}
