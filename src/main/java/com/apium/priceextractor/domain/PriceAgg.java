package com.apium.priceextractor.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import com.apium.priceextractor.domain.exception.InvalidPriceWithPriorityException;
import com.apium.priceextractor.domain.exception.PriceAggException;
import org.springframework.lang.NonNull;

public record PriceAgg(PriceId id, BrandId brandId, Date startDate, Date endDate, ProductId productId, Priority priority,
                       PositiveMonetaryAmount positiveMonetaryAmount) {

  //TODO vamos a crear un assert en el enunciado que este directamente en el agreado
  public PriceAgg(
      @NonNull PriceId id,
      @NonNull BrandId brandId,
      @NonNull Date startDate,
      @NonNull Date endDate,
      @NonNull ProductId productId,
      @NonNull Priority priority,
      @NonNull PositiveMonetaryAmount positiveMonetaryAmount
  ) {
    this.assertDateRangeIsValid(startDate, endDate);
    this.assertPriceGreaterThan2Digits(priority, positiveMonetaryAmount);
    this.id = id;
    this.brandId = brandId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.productId = productId;
    this.priority = priority;
    this.positiveMonetaryAmount = positiveMonetaryAmount;
  }

  //TODO extraemos esto a un DateRange
  private void assertDateRangeIsValid(Date startDate, Date endDate) {
    if (!startDate.before(endDate)) {
      throw new RuntimeException("DomainError: startDate can not bee newer than endDate");
    }
  }

  //TODO extraemos esto a PositiveMonetaryAmount
  private void assertPriceGreaterThan2Digits(Priority priority, PositiveMonetaryAmount positiveMonetaryAmount) {
    Long amount = positiveMonetaryAmount.value().getNumber().numberValue(Long.class);
    if (countDigits(amount) > 2 && priority.value() <= 10) {
      throw new InvalidPriceWithPriorityException("DomainError: price has more than 2 digits and priority is less than 10");
    }
  }

  private Integer countDigits(Long number) {
    String numberString = Long.toString(number);
    return numberString.length();
  }

  public PriceAgg setProductDiscountId(ProductDiscountAgg productDiscountAgg) {
    if (productDiscountAgg.productId().equals(this.productId)) {
      return new PriceAgg(
          this.id,
          this.brandId,
          this.startDate,
          this.endDate,
          this.productId,
          this.priority,
          this.positiveMonetaryAmount()
      );
    }
    throw new PriceAggException("productDiscountAgg is not applicable to this priceAgg because productIds are different");
  }

  //TODO Double dispatch recibiremos el ProductDisctount
  public PriceAgg applyDiscount(PositiveMonetaryAmount positiveMonetaryAmount) {
    return new PriceAgg(
        this.id,
        this.brandId,
        this.startDate,
        this.endDate,
        this.productId,
        this.priority,
        positiveMonetaryAmount
    );
  }

  public PriceDto toDto(SimpleDateFormat simpleDateFormat) {
    return new PriceDto(
        id.toString(),
        productId.toString(),
        brandId.toString(),
        simpleDateFormat.format(startDate),
        simpleDateFormat.format(endDate),
        positiveMonetaryAmount.value().getNumber().doubleValue()
    );
  }

}
