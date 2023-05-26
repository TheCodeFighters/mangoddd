package com.apium.priceextractor.application.priceDiscount;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.DomainEventPublisher;
import com.apium.priceextractor.domain.ProductDiscountERPRepository;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.domain.event.EventId;
import com.apium.priceextractor.domain.event.ProductDiscountAggWasCreatedEvent;
import jakarta.transaction.Transactional;

public class ProductPriceDiscountService {

  private final ProductDiscountERPRepository productDiscountERPRepository;

  private final ProductDiscountRepository productPriceDiscountRepository;

  private final DomainEventPublisher domainEventPublisher;

  public ProductPriceDiscountService(ProductDiscountERPRepository productDiscountERPRepository,
      ProductDiscountRepository productPriceDiscountRepository, DomainEventPublisher domainEventPublisher) {
    this.productDiscountERPRepository = productDiscountERPRepository;
    this.productPriceDiscountRepository = productPriceDiscountRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  @Transactional
  public void CreateProductPriceDiscount(CreateProductDiscountRequestDto requestDto) {

    productDiscountERPRepository.findOrFailByProductIdOrBrandId(
        ProductId.fromString(requestDto.productId()),
        BrandId.fromString(requestDto.brandId())
    ).ifPresent(productDiscountAgg -> {

      productPriceDiscountRepository.save(productDiscountAgg);
      domainEventPublisher.send(
          new ProductDiscountAggWasCreatedEvent(EventId.create(), productDiscountAgg.toDpo())
      );

    });
  }

}
