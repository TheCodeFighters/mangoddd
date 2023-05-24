package com.apium.priceextractor.domain;

import org.springframework.lang.NonNull;

public interface ProductDiscountRepository {

  ProductDiscountId DEFAULT_PRODUCT_DISCOUNT_ID = ProductDiscountId.fromString("242205c3-819d-40fd-8a8e-89b153c62330");

  ProductDiscountAgg findOrDefaultByProductId(@NonNull ProductId productId);
}
