package com.inditex.priceextractor.domain;

public interface ProductDiscountRetriever {

  ProductDiscountAgg retrieveProductDiscount(ProductId productId);
}
