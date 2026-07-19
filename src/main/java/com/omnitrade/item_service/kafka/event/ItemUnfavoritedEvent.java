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
public class ItemUnfavoritedEvent extends BaseEvent {

    private Long itemId;

    private UUID userId;

    private Integer favoriteCount;

    private Instant unfavoritedAt;
}