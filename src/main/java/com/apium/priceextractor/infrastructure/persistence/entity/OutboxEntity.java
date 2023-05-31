package com.apium.priceextractor.infrastructure.persistence.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "outbox_table")
public class OutboxEntity {

  @Id
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(name = "occurred_on", nullable = false)
  @NotNull
  private Date occurredOn;

  @Column(name = "type", nullable = false)
  @NotNull
  private String type;

  @Column(name = "topic_name", nullable = false)
  @NotNull
  private String topicName;

  @Column(name = "payload", nullable = false)
  @NotNull
  private String payload;

  public UUID getId() {
    return id;
  }
}
