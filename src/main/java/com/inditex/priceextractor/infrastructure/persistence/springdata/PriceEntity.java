package com.inditex.priceextractor.infrastructure.persistence.springdata;

import java.util.Currency;
import java.util.Date;

import com.inditex.priceextractor.domain.PriceAggregate;
import com.inditex.priceextractor.domain.Priority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import javax.money.Monetary;

@Entity
@Table(name = "prices")
public class PriceEntity {

  @Id
  @Column(name = "price_list")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "brand_id", nullable = false)
  private long brandId;

  @Column(name = "start_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @NotNull
  private Date startDate;

  @Column(name = "end_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @NotNull
  private Date endDate;

  @Column(name = "product_id", nullable = false)
  private long productId;

  @Column(name = "priority", nullable = false)
  private int priority;

  @Column(name = "price", nullable = false)
  private double price;

  @Column(name = "curr", nullable = false)
  @NotNull
  private Currency curr;

  public PriceAggregate toPrice() {
    return new PriceAggregate(
        id,
        brandId,
        startDate,
        endDate,
        productId,
        new Priority(priority),
        Monetary.getDefaultAmountFactory().setCurrency(curr.getCurrencyCode()).setNumber(price).create()
    );
  }

}
