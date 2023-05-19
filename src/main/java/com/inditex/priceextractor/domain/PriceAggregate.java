package com.inditex.priceextractor.domain;

import java.util.Date;
import java.util.Objects;

import javax.money.MonetaryAmount;

public class PriceAggregate {

  private Long id;

  private Long brandId;

  private Date startDate;

  private Date endDate;

  private Long productId;

  private Priority priority;

  private MonetaryAmount monetaryAmount;

  public PriceAggregate(
      Long id,
      Long brandId,
      Date startDate,
      Date endDate,
      Long productId,
      Priority priority,
      MonetaryAmount monetaryAmount
  ) {
    this.assertDateRangeIsValid(startDate, endDate);
    this.assertPriceGreaterThan2Digits(priority, monetaryAmount);
    this.id = id;
    this.brandId = brandId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.productId = productId;
    this.priority = priority;
    this.monetaryAmount = monetaryAmount;
  }

  private void assertDateRangeIsValid(Date startDate, Date endDate) {
    if (!startDate.before(endDate)) {
      throw new RuntimeException("DomainError: startDate can not bee newer than endDate");
    }
  }

  private void assertPriceGreaterThan2Digits(Priority priority, MonetaryAmount monetaryAmount) {
    Long amount = monetaryAmount.getNumber().numberValue(Long.class);
    if (countDigits(amount) > 2 && priority.getValue() <= 10) {
      throw new InvalidPriceWithPriorityException("DomainError: price is greater than 999.99 and priority is less than 10");
    }
  }

  private Integer countDigits(Long number) {
    String numberString = Long.toString(number);
    return numberString.length();
  }

  public long getBrandId() {
    return brandId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public long getId() {
    return id;
  }

  public long getProductId() {
    return productId;
  }

  public Priority getPriority() {
    return priority;
  }

  public MonetaryAmount getMonetaryAmount() {
    return monetaryAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PriceAggregate that = (PriceAggregate) o;
    return Objects.equals(id, that.id) && Objects.equals(brandId, that.brandId) && Objects.equals(startDate,
        that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(productId, that.productId)
        && Objects.equals(priority, that.priority) && Objects.equals(monetaryAmount, that.monetaryAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, brandId, startDate, endDate, productId, priority, monetaryAmount);
  }
}
