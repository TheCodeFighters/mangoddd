package com.inditex.priceextractor.domain;

public interface ProductDiscountERPRepository {
  ProductDiscountAgg findAllOrFailByBrandId(BrandId brandId);
}
