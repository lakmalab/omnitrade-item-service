package com.omnitrade.item_service.kafka.event;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ItemViewedEvent extends BaseEvent {

    private Long itemId;

    private Integer viewCount;

    private Instant viewedAt;
}