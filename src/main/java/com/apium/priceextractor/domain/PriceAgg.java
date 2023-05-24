package com.apium.priceextractor.domain;

import java.text.SimpleDateFormat;

import com.apium.priceextractor.domain.exception.InvalidPriceWithPriorityException;
import org.springframework.lang.NonNull;

public record PriceAgg(PriceId id, BrandId brandId, DateRange dateRange, ProductId productId, Priority priority,
                       PositiveMonetaryAmount positiveMonetaryAmount) {

  public PriceAgg(
      @NonNull PriceId id,
      @NonNull BrandId brandId,
      @NonNull DateRange dateRange,
      @NonNull ProductId productId,
      @NonNull Priority priority,
      @NonNull PositiveMonetaryAmount positiveMonetaryAmount
  ) {
    this.assertPriceGreaterThan2Digits(priority, positiveMonetaryAmount);
    this.id = id;
    this.brandId = brandId;
    this.dateRange = dateRange;
    this.productId = productId;
    this.priority = priority;
    this.positiveMonetaryAmount = positiveMonetaryAmount;
  }

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

  public PriceAgg applyDiscount(PositiveMonetaryAmount positiveMonetaryAmount) {
    return new PriceAgg(
        this.id,
        this.brandId,
        this.dateRange,
        this.productId,
        this.priority,
        positiveMonetaryAmount
    );
  }

  public PriceDto toDto() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
    return new PriceDto(
        id.toString(),
        productId.toString(),
        brandId.toString(),
        simpleDateFormat.format(dateRange.startDate().date()),
        simpleDateFormat.format(dateRange.endDate().date()),
        positiveMonetaryAmount.value().getNumber().doubleValue()
    );
  }

}
