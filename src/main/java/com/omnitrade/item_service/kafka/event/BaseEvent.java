package com.omnitrade.item_service.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID eventId;
    private Instant timestamp;
    private String eventType;

    @Builder.Default
    private Integer version = 1;
    @Builder.Default
    private String source = "item-service";

    private String correlationId;
    private String traceId;
}