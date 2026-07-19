package com.omnitrade.item_service.kafka.event;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDeletedEvent extends BaseEvent {

    private Long itemId;

    private UUID sellerId;

    private Instant deletedAt;
}