package com.apium.priceextractor.infrastructure.persistence.springdata;

import java.util.UUID;

import com.apium.priceextractor.domain.DiscountPercentage;
import com.apium.priceextractor.domain.PositiveNumber;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountId;
import com.apium.priceextractor.domain.ProductId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product_discounts")
public class ProductDiscountEntity {

  @Id
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(name = "product_id", nullable = false, columnDefinition = "BINARY(16)")
  @NotNull
  private UUID productId;

  @Column(name = "discount_percentage", nullable = false)
  @NotNull
  private Double discountPercentage;

  public ProductDiscountAgg toProductDiscountAgg() {
    return new ProductDiscountAgg(
        new ProductDiscountId(id),
        new ProductId(productId),
        new DiscountPercentage(
            new PositiveNumber(discountPercentage)
        )
    );
  }

}
