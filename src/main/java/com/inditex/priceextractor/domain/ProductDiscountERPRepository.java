package com.inditex.priceextractor.domain;

public interface ProductDiscountERPRepository {

  ProductDiscountAgg retrieveProductDiscount(ProductId productId);
}
