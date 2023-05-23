package com.inditex.priceextractor.infrastructure.persistence.springdata;

import java.util.Currency;
import java.util.Date;
import java.util.UUID;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PositiveMonetaryAmount;
import com.inditex.priceextractor.domain.PriceAgg;
import com.inditex.priceextractor.domain.PriceId;
import com.inditex.priceextractor.domain.Priority;
import com.inditex.priceextractor.domain.ProductDiscountId;
import com.inditex.priceextractor.domain.ProductId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
  @Column(name = "price_list", columnDefinition = "BINARY(16)")
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

  @Column(name = "product_discount_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID productDiscountId;

  public PriceAgg toPrice() {
    return new PriceAgg(
        new PriceId(id),
        new BrandId(brandId),
        startDate,
        endDate,
        new ProductId(productId),
        new Priority(priority),
        PositiveMonetaryAmount.fromDoubleAndCurrency(price, curr.getCurrencyCode()),
        new ProductDiscountId(productDiscountId)
    );
  }

}
