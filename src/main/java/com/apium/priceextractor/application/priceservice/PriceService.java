package com.apium.priceextractor.application.priceservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.PriceAgg;
import com.apium.priceextractor.domain.PriceDto;
import com.apium.priceextractor.domain.PriceRepository;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.domain.exception.DateFormatException;
import com.apium.priceextractor.domain.exception.DomainEntityNotFoundException;
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

  public PriceDto getCurrentPrice(GetCurrentPriceRequestDto request) {
    Date applicationDate;
    try {//TODO igual que lo de abajo
      applicationDate = simpleDateFormat.parse(request.applicationDate());
    } catch (ParseException e) {
      throw new DateFormatException("Date format is not valid");
    }
    PriceAgg priceAgg = priceRepository.findOrFailRate(
        ProductId.fromString(request.productId()),
        BrandId.fromString(request.brandId()),
        applicationDate
    );

    try {
      ProductDiscountAgg productDiscountAgg = productDiscountRepository.findOrDefaultByProductId(priceAgg.productId());
      priceAgg = priceAgg.applyDiscount(productDiscountAgg.applyDiscount(priceAgg.positiveMonetaryAmount(), priceAgg.brandId()));
    } catch (DomainEntityNotFoundException ignored) {
    }
    return priceAgg.toDto(simpleDateFormat);

  }
}
