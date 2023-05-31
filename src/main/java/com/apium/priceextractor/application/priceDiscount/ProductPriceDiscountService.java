package com.apium.priceextractor.application.priceDiscount;

import com.apium.priceextractor.domain.Date;
import com.apium.priceextractor.domain.DiscountPercentage;
import com.apium.priceextractor.domain.DomainEventPublisher;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountId;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.domain.event.EventId;
import com.apium.priceextractor.domain.event.ProductDiscountAggWasCreatedEvent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceDiscountService {

  private final ProductDiscountRepository productPriceDiscountRepository;

  private final DomainEventPublisher domainEventPublisher;

  public ProductPriceDiscountService(
      ProductDiscountRepository productPriceDiscountRepository, DomainEventPublisher domainEventPublisher) {
    this.productPriceDiscountRepository = productPriceDiscountRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  @Transactional
  public void createProductPriceDiscount(CreateProductDiscountRequestDto requestDto) {
    ProductDiscountAgg productDiscountAgg = new ProductDiscountAgg(
        ProductDiscountId.fromString(requestDto.productId()),
        ProductId.fromString(requestDto.brandId()),
        DiscountPercentage.fromDouble(requestDto.discountPercentage())
    );
    productPriceDiscountRepository.save(productDiscountAgg);
    domainEventPublisher.publish(
        new ProductDiscountAggWasCreatedEvent(EventId.create(), Date.Now(), productDiscountAgg.toDto())
    );
  }

}
