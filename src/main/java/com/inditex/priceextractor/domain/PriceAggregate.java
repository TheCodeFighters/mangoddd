package com.inditex.priceextractor.domain;

import java.util.Currency;
import java.util.Date;
import java.util.Objects;

public class PriceAggregate {

  private Long id;

  private Long brandId;

  private Date startDate;

  private Date endDate;

  private Long productId;

  private Integer priority;

  private Double price;

  private Currency curr;

  public PriceAggregate(
      long id,
      long brandId,
      Date startDate,
      Date endDate,
      long productId,
      int priority,
      double price,
      Currency curr
  ) {
    this.id = id;
    this.assertDateRangeIsValid(startDate, endDate);
    this.brandId = brandId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.productId = productId;
    this.priority = priority;
    this.price = price;
    this.curr = curr;
  }

  private void assertDateRangeIsValid(Date startDate, Date endDate) throws RuntimeException {
    if (!startDate.before(endDate)) {
      throw new RuntimeException("DomainError: startDate can not bee newer than endDate");
    }
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

  public int getPriority() {
    return priority;
  }

  public double getPrice() {
    return price;
  }

  public Currency getCurr() {
    return curr;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PriceAggregate priceAggregate1 = (PriceAggregate) o;
    return id == priceAggregate1.id && brandId == priceAggregate1.brandId && productId == priceAggregate1.productId
        && priority == priceAggregate1.priority
        && Double.compare(priceAggregate1.price, price) == 0 && startDate.equals(priceAggregate1.startDate) && endDate.equals(
        priceAggregate1.endDate)
        && curr.equals(priceAggregate1.curr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, brandId, startDate, endDate, productId, priority, price, curr);
  }
}
