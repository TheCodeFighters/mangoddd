package com.apium.priceextractor.infrastructure.persistence.jdbctemplate;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apium.priceextractor.domain.event.DomainEvent;
import com.apium.priceextractor.domain.exception.EventTypeException;
import com.apium.priceextractor.infrastructure.eda.messagerelay.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateOutboxRepository implements OutboxRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  private final DomainEventExtractor domainEventExtractor;

  private final ObjectMapper objectMapper;

  private final SimpleDateFormat sdf;

  public JdbcTemplateOutboxRepository(NamedParameterJdbcTemplate jdbcTemplate, DomainEventExtractor domainEventExtractor,
      ObjectMapper objectMapper,
      SimpleDateFormat sdf) {
    this.jdbcTemplate = jdbcTemplate;
    this.domainEventExtractor = domainEventExtractor;
    this.objectMapper = objectMapper;
    this.sdf = sdf;
  }

  @Override
  public void publish(DomainEvent event) {
    String sql =
        "INSERT INTO outbox_table (id, occurred_on,type, topic_name, payload) VALUES (UUID_TO_BIN(:id), :occurredOn,:type, :topicName, "
            + ":payload)";

    Map<String, Object> params = new HashMap<>();
    params.put("id", event.getId().toString());
    params.put("occurredOn", sdf.format(event.getOccurredOn().date().getTime()));
    params.put("type", event.getType());
    params.put("topicName", event.getTopicName().toString());
    try {
      params.put("payload", objectMapper.writeValueAsString(event.getPayload()));
    } catch (JsonProcessingException e) {
      throw new EventTypeException("event Format Exception");
    }
    jdbcTemplate.update(sql, params);
  }

  @Override
  public List<DomainEvent> findAllMessages() {
    String sql = "SELECT BIN_TO_UUID(id) as id, occurred_on,type, topic_name, payload FROM outbox_table LIMIT 1000";
    return jdbcTemplate.query(sql, (resultSet, rowNum) -> domainEventExtractor.createFromType(resultSet));
  }

  @Override
  public void updateMessageStatus(DomainEvent event) {
    String sql = "DELETE FROM outbox_table WHERE id = UUID_TO_BIN(:id)";
    Map<String, Object> params = new HashMap<>();
    params.put("id", event.getId().toString());

    jdbcTemplate.update(sql, params);
  }
}