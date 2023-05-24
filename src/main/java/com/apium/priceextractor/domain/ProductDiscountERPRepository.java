package com.apium.priceextractor.domain;

public interface ProductDiscountERPRepository {
  ProductDiscountAgg findAllOrFailByBrandId(BrandId brandId);
}
