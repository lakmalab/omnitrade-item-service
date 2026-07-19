package com.omnitrade.item_service.kafka.mapper;

import com.omnitrade.item_service.kafka.event.*;
import com.omnitrade.item_service.model.entity.Item;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ItemEventMapper {

    public ItemCreatedEvent toItemCreatedEvent(Item item) {

        return ItemCreatedEvent.builder()
                .eventId(UUID.randomUUID())
                .timestamp(Instant.now())
                .eventType("ITEM_CREATED")

                .itemId(item.getId())
                .sellerId(item.getSellerId())
                .categoryId(item.getCategoryId())
                .title(item.getTitle())
                .description(item.getDescription())
                .price(item.getPrice())
                .city(item.getCity())
                .district(item.getDistrict())
                .condition(item.getCondition())
                .allowTrade(item.getAllowTrade())
                .allowOffers(item.getAllowOffers())
                .createdAt(Instant.from(item.getCreatedAt()))
                .build();
    }

    public ItemUpdatedEvent toItemUpdatedEvent(Item item) {

        return ItemUpdatedEvent.builder()
                .eventId(UUID.randomUUID())
                .timestamp(Instant.now())
                .eventType("ITEM_UPDATED")

                .itemId(item.getId())
                .sellerId(item.getSellerId())
                .categoryId(item.getCategoryId())
                .title(item.getTitle())
                .description(item.getDescription())
                .price(item.getPrice())
                .city(item.getCity())
                .district(item.getDistrict())
                .condition(item.getCondition())
                .allowTrade(item.getAllowTrade())
                .allowOffers(item.getAllowOffers())
                .updatedAt(Instant.from(item.getUpdatedAt()))
                .build();
    }

    public ItemDeletedEvent toItemDeletedEvent(Item item) {

        return ItemDeletedEvent.builder()
                .eventId(UUID.randomUUID())
                .timestamp(Instant.now())
                .eventType("ITEM_DELETED")

                .itemId(item.getId())
                .sellerId(item.getSellerId())
                .deletedAt(Instant.now())
                .build();
    }

    public ItemViewedEvent toItemViewedEvent(Item item) {

        return ItemViewedEvent.builder()
                .eventId(UUID.randomUUID())
                .timestamp(Instant.now())
                .eventType("ITEM_VIEWED")

                .itemId(item.getId())
                .viewCount(item.getViewCount())
                .viewedAt(Instant.now())
                .build();
    }

    public ItemFavoritedEvent toItemFavoritedEvent(Item item, UUID userId) {

        return ItemFavoritedEvent.builder()
                .eventId(UUID.randomUUID())
                .timestamp(Instant.now())
                .eventType("ITEM_FAVORITED")

                .itemId(item.getId())
                .userId(userId)
                .favoriteCount(item.getFavoriteCount())
                .favoritedAt(Instant.now())
                .build();
    }

    public ItemUnfavoritedEvent toItemUnfavoritedEvent(Item item, UUID userId) {

        return ItemUnfavoritedEvent.builder()
                .eventId(UUID.randomUUID())
                .timestamp(Instant.now())
                .eventType("ITEM_UNFAVORITED")

                .itemId(item.getId())
                .userId(userId)
                .favoriteCount(item.getFavoriteCount())
                .unfavoritedAt(Instant.now())
                .build();
    }

}