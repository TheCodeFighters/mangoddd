package com.inditex.priceextractor.domain;

import org.springframework.lang.NonNull;

public interface ProductDiscountRepository {

  ProductDiscountAgg findOrFailByProductId(@NonNull ProductId productId);
}
