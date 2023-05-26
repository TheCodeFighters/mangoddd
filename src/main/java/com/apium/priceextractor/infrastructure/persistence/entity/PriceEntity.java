package com.apium.priceextractor.infrastructure.persistence.entity;

import java.util.Currency;
import java.util.Date;
import java.util.UUID;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.DateRange;
import com.apium.priceextractor.domain.PositiveMonetaryAmount;
import com.apium.priceextractor.domain.PriceAgg;
import com.apium.priceextractor.domain.PriceId;
import com.apium.priceextractor.domain.Priority;
import com.apium.priceextractor.domain.ProductId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "prices")
public class PriceEntity {

  @Id
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(name = "brand_id", nullable = false, columnDefinition = "BINARY(16)")
  @NotNull
  private UUID brandId;

  @Column(name = "start_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @NotNull
  private Date startDate;

  @Column(name = "end_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @NotNull
  private Date endDate;

  @Column(name = "product_id", nullable = false, columnDefinition = "BINARY(16)")
  @NotNull
  private UUID productId;

  @Column(name = "priority", nullable = false)
  @NotNull
  private Integer priority;

  @Column(name = "price", nullable = false)
  @NotNull
  private Double price;

  @Column(name = "curr", nullable = false)
  @NotNull
  private Currency curr;

  public PriceAgg toPrice() {
    return new PriceAgg(
        new PriceId(id),
        new BrandId(brandId),
        new DateRange(new com.apium.priceextractor.domain.Date(startDate), new com.apium.priceextractor.domain.Date(endDate)),
        new ProductId(productId),
        new Priority(priority),
        PositiveMonetaryAmount.fromDoubleAndCurrency(price, curr.getCurrencyCode())
    );
  }

}
