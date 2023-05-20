package com.inditex.priceextractor.domain;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class PriceAgg {

  private PriceId id;

  private BrandId brandId;

  private Date startDate;

  private Date endDate;

  private ProductId productId;

  private Priority priority;

  private PositiveMonetaryAmount positiveMonetaryAmount;

  public PriceAgg(
      PriceId id,
      BrandId brandId,
      Date startDate,
      Date endDate,
      ProductId productId,
      Priority priority,
      PositiveMonetaryAmount positiveMonetaryAmount
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

  private void assertDateRangeIsValid(Date startDate, Date endDate) {
    if (!startDate.before(endDate)) {
      throw new RuntimeException("DomainError: startDate can not bee newer than endDate");
    }
  }

  private void assertPriceGreaterThan2Digits(Priority priority, PositiveMonetaryAmount positiveMonetaryAmount) {
    Long amount = positiveMonetaryAmount.value().getNumber().numberValue(Long.class);
    if (countDigits(amount) > 2 && priority.getValue() <= 10) {
      throw new InvalidPriceWithPriorityException("DomainError: price is greater than 999.99 and priority is less than 10");
    }
  }

  private Integer countDigits(Long number) {
    String numberString = Long.toString(number);
    return numberString.length();
  }

  public PriceId getId() {
    return id;
  }

  public BrandId getBrandId() {
    return brandId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public ProductId getProductId() {
    return productId;
  }

  public Priority getPriority() {
    return priority;
  }

  public PositiveMonetaryAmount getPositiveMonetaryAmount() {
    return positiveMonetaryAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PriceAgg priceAgg = (PriceAgg) o;
    return Objects.equals(id, priceAgg.id) && Objects.equals(brandId, priceAgg.brandId) && Objects.equals(
        startDate, priceAgg.startDate) && Objects.equals(endDate, priceAgg.endDate) && Objects.equals(productId,
        priceAgg.productId) && Objects.equals(priority, priceAgg.priority) && Objects.equals(positiveMonetaryAmount,
        priceAgg.positiveMonetaryAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, brandId, startDate, endDate, productId, priority, positiveMonetaryAmount);
  }
}
