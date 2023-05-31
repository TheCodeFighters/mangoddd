package com.apium.priceextractor.application.priceDiscount;

import com.apium.priceextractor.domain.DiscountPercentage;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountId;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.domain.ProductId;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceDiscountService {

  private final ProductDiscountRepository productPriceDiscountRepository;


  public ProductPriceDiscountService(
      ProductDiscountRepository productPriceDiscountRepository) {
    this.productPriceDiscountRepository = productPriceDiscountRepository;
  }

  public void createProductPriceDiscount(CreateProductDiscountRequestDto requestDto) {
    ProductDiscountAgg productDiscountAgg = new ProductDiscountAgg(
        ProductDiscountId.fromString(requestDto.productId()),
        ProductId.fromString(requestDto.brandId()),
        DiscountPercentage.fromDouble(requestDto.discountPercentage())
    );
    productPriceDiscountRepository.save(productDiscountAgg);
  }

}
