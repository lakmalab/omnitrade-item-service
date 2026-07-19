package com.omnitrade.item_service.kafka.event;

import com.omnitrade.item_service.model.enums.ItemCondition;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreatedEvent extends BaseEvent {

    private Long itemId;
    private UUID sellerId;
    private Long categoryId;
    private String title;
    private String description;
    private BigDecimal price;
    private String city;
    private String district;
    private ItemCondition condition;
    private Boolean allowTrade;
    private Boolean allowOffers;
    private Instant createdAt;
}