package com.inditex.priceextractor.domain;

import java.util.Currency;
import java.util.Date;
import java.util.Objects;

public class Price {

  private Long id;

  private Long brandId;

  private Date startDate;

  private Date endDate;

  private Long productId;

  private Integer priority;

  private Double price;

  private Currency curr;

  public Price(
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
    Price price1 = (Price) o;
    return id == price1.id && brandId == price1.brandId && productId == price1.productId && priority == price1.priority
        && Double.compare(price1.price, price) == 0 && startDate.equals(price1.startDate) && endDate.equals(price1.endDate)
        && curr.equals(price1.curr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, brandId, startDate, endDate, productId, priority, price, curr);
  }
}
