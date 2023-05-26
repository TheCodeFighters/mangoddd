package com.apium.priceextractor.domain;

import java.util.Optional;

public interface ProductDiscountERPRepository {

  Optional<ProductDiscountAgg> findOrFailByProductIdOrBrandId(ProductId productId, BrandId brandId);
}
