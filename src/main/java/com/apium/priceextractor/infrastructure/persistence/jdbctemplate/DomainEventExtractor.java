package com.apium.priceextractor.infrastructure.persistence.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.apium.priceextractor.domain.dto.ProductDiscountDto;
import com.apium.priceextractor.domain.event.DomainEvent;
import com.apium.priceextractor.domain.event.EventId;
import com.apium.priceextractor.domain.event.ProductDiscountAggWasCreatedEvent;
import com.apium.priceextractor.domain.exception.EventTypeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class DomainEventExtractor {

  private final ObjectMapper objectMapper;

  public DomainEventExtractor(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public DomainEvent createFromType(ResultSet resultSet) {
    try {
      String type = resultSet.getString("type");
      if (type.equals(ProductDiscountAggWasCreatedEvent.class.getName())) {
        EventId eventId = new EventId(UUID.fromString(resultSet.getString("id")));
        Date occurredOn = new Date(resultSet.getTimestamp("occurred_on").getTime());
        ProductDiscountDto payload = objectMapper.readValue(resultSet.getString("payload"), ProductDiscountDto.class);
        return new ProductDiscountAggWasCreatedEvent(
            eventId,
            new com.apium.priceextractor.domain.Date(occurredOn),
            payload
        );
      }

    } catch (SQLException | JsonProcessingException e) {
      throw new EventTypeException("event Format Exception");
    }
    throw new EventTypeException("event Type Not Found");
  }

}
