package com.inditex.priceextractor.domain;

import java.util.Optional;

import org.springframework.lang.NonNull;

public interface ProductDiscountRepository {

  ProductDiscountAgg findByProductDiscountId(@NonNull ProductDiscountId productDiscountId);
}
