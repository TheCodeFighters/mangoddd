package com.inditex.priceextractor.infrastructure.erp;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.ProductDiscountAgg;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.domain.ProductDiscountERPRepository;

public class OdooProductDiscountRepository implements ProductDiscountERPRepository {

  public ProductDiscountAgg findAllOrFailByBrandId(BrandId brandId) {
    //TODO not implemented yet
    return null;
  }
}
