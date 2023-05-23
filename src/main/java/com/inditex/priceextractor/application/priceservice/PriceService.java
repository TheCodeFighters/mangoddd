package com.inditex.priceextractor.application.priceservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PriceAgg;
import com.inditex.priceextractor.domain.PriceRepository;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.domain.exception.DateFormatException;
import com.inditex.priceextractor.domain.exception.DomainException;

import org.springframework.stereotype.Service;

@Service
public class PriceService {

  private final PriceRepository priceRepository;

  private final SimpleDateFormat simpleDateFormat;

  //  private final ProductDiscountRepository productDiscountRepository;

  public PriceService(
      PriceRepository priceRepository,
      SimpleDateFormat simpleDateFormat
      //      ProductDiscountRepository productDiscountRepository
  ) {
    this.priceRepository = priceRepository;
    this.simpleDateFormat = simpleDateFormat;
    //    this.productDiscountRepository = productDiscountRepository;
  }

  public PriceDto getCurrentPrice(GetCurrentPriceRequestDto request) {
    try {
      Date applicationDate = simpleDateFormat.parse(request.applicationDate());
      PriceAgg priceAgg = priceRepository.findOrfailRate(
          new ProductId(UUID.fromString(request.productId())),
          new BrandId(UUID.fromString(request.brandId())),
          applicationDate
      );

      //    ProductDiscountAgg productDiscountAgg = productDiscountRepository.findByProductDiscountId(priceAgg.getProductDiscountId());
      //    productDiscountAgg.applyDiscount(priceAgg.getPositiveMonetaryAmount(), priceAgg.getBrandId());
      return PriceDto.fromPrice(simpleDateFormat, priceAgg);
    } catch (ParseException e) {
      throw new DateFormatException("Date format is not valid");
    }
  }
}
