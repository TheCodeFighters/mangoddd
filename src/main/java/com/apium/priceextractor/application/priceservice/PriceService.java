package com.apium.priceextractor.application.priceservice;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.Date;
import com.apium.priceextractor.domain.PriceAgg;
import com.apium.priceextractor.domain.PriceDto;
import com.apium.priceextractor.domain.PriceRepository;
import com.apium.priceextractor.domain.ProductId;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

  private final PriceRepository priceRepository;

  public PriceService(
      PriceRepository priceRepository
  ) {
    this.priceRepository = priceRepository;
  }

  public PriceDto getCurrentPrice(GetCurrentPriceRequestDto request) {
    Date applicationDate = Date.fromString(request.applicationDate());

    PriceAgg priceAgg = priceRepository.findOrFailRate(
        ProductId.fromString(request.productId()),
        BrandId.fromString(request.brandId()),
        applicationDate
    );

    return priceAgg.toDto();

  }
}
