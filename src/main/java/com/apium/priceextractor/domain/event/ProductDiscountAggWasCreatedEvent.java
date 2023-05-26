package com.apium.priceextractor.domain.event;

import com.apium.priceextractor.domain.dpo.ProductDiscountDpo;

public class ProductDiscountAggWasCreatedEvent extends DomainEvent {

  public ProductDiscountAggWasCreatedEvent(EventId id, ProductDiscountDpo productDiscountDpo) {
    super(id, productDiscountDpo);
  }

}

