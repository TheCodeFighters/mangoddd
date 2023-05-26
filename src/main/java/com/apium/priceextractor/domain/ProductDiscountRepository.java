package com.apium.priceextractor.domain;

import org.springframework.lang.NonNull;

public interface ProductDiscountRepository {

  ProductDiscountAgg findOrDefaultByProductId(@NonNull ProductId productId);

  void save(@NonNull ProductDiscountAgg productDiscountAgg);
}
