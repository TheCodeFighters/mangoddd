package com.apium.priceextractor.domain.event;

import com.apium.priceextractor.domain.Date;
import com.apium.priceextractor.domain.dto.ProductDiscountDto;

public class ProductDiscountAggWasCreatedEvent extends DomainEvent {

  private final static TopicName TOPIC_NAME = new TopicName("product_discount_agg");

  public ProductDiscountAggWasCreatedEvent(EventId id, Date date, ProductDiscountDto productDiscountDto) {
    super(id, date, TOPIC_NAME, productDiscountDto);
  }
}

