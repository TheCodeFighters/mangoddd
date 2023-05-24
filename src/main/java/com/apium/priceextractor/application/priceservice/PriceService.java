package com.apium.priceextractor.application.priceservice;

import java.text.SimpleDateFormat;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.Date;
import com.apium.priceextractor.domain.PriceAgg;
import com.apium.priceextractor.domain.PriceDto;
import com.apium.priceextractor.domain.PriceRepository;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.domain.ProductId;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

  private final PriceRepository priceRepository;

  private final ProductDiscountRepository productDiscountRepository;

  public PriceService(
      PriceRepository priceRepository,
      ProductDiscountRepository productDiscountRepository
  ) {
    this.priceRepository = priceRepository;
    this.productDiscountRepository = productDiscountRepository;
  }

  public PriceDto getCurrentPrice(GetCurrentPriceRequestDto request) {
    Date applicationDate = Date.fromString(request.applicationDate());

    PriceAgg priceAgg = priceRepository.findOrFailRate(
        ProductId.fromString(request.productId()),
        BrandId.fromString(request.brandId()),
        applicationDate
    );

    ProductDiscountAgg productDiscountAgg = productDiscountRepository.findOrDefaultByProductId(priceAgg.productId());
    priceAgg = priceAgg.applyDiscount(productDiscountAgg.applyDiscount(priceAgg.positiveMonetaryAmount(), priceAgg.brandId()));

    return priceAgg.toDto();

  }
}
