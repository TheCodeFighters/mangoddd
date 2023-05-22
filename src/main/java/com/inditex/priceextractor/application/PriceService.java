package com.inditex.priceextractor.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PriceAgg;
import com.inditex.priceextractor.domain.PriceRepository;
import com.inditex.priceextractor.domain.ProductDiscountAgg;
import com.inditex.priceextractor.domain.ProductDiscountRepository;
import com.inditex.priceextractor.domain.ProductId;

import org.springframework.stereotype.Service;

@Service
public class PriceService {

  private final PriceRepository priceRepository;

  private final SimpleDateFormat simpleDateFormat;

  private final ProductDiscountRepository productDiscountRepository;

  public PriceService(
      PriceRepository priceRepository,
      SimpleDateFormat simpleDateFormat,
      ProductDiscountRepository productDiscountRepository
  ) {
    this.priceRepository = priceRepository;
    this.simpleDateFormat = simpleDateFormat;
    this.productDiscountRepository = productDiscountRepository;
  }

  public PriceDto getCurrentPrice(GetCurrentPriceRequestDto request) throws ParseException, RuntimeException {
    Date applicationDate = simpleDateFormat.parse(request.applicationDate());
    Optional<PriceAgg> priceOpt = priceRepository.findRate(
        new ProductId(UUID.fromString(request.productId())),
        new BrandId(UUID.fromString(request.brandId())),
        applicationDate
    );
    if (priceOpt.isPresent()) {
      PriceAgg priceAgg = priceOpt.get();
      Optional<ProductDiscountAgg> productDiscountAgg = productDiscountRepository.findByProductDiscountId(priceAgg.getProductDiscountId());
      productDiscountAgg.ifPresent(discountAgg -> priceAgg.setPositiveMonetaryAmount(
              discountAgg.applyDiscount(priceAgg.getPositiveMonetaryAmount(), priceAgg.getBrandId())
          )
      );
      return PriceDto.fromPrice(simpleDateFormat, priceOpt.get());
    } else {
      throw new RuntimeException();
    }
  }
}
