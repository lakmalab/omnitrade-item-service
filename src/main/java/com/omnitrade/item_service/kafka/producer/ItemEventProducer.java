package com.omnitrade.item_service.kafka.producer;

import com.omnitrade.item_service.config.KafkaTopics;
import com.omnitrade.item_service.kafka.event.BaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishItemCreated(BaseEvent event, Long itemId) {

        kafkaTemplate.send(
                KafkaTopics.ITEM_CREATED,
                itemId.toString(),
                event
        );

        log.info("Published ITEM_CREATED event for item {}", itemId);
    }

    public void publishItemUpdated(BaseEvent event, Long itemId) {

        kafkaTemplate.send(
                KafkaTopics.ITEM_UPDATED,
                itemId.toString(),
                event
        );

        log.info("Published ITEM_UPDATED event for item {}", itemId);
    }

    public void publishItemDeleted(BaseEvent event, Long itemId) {

        kafkaTemplate.send(
                KafkaTopics.ITEM_DELETED,
                itemId.toString(),
                event
        );

        log.info("Published ITEM_DELETED event for item {}", itemId);
    }

    public void publishItemViewed(BaseEvent event, Long itemId) {

        kafkaTemplate.send(
                KafkaTopics.ITEM_VIEWED,
                itemId.toString(),
                event
        );

        log.info("Published ITEM_VIEWED event for item {}", itemId);
    }

    public void publishItemFavorited(BaseEvent event, Long itemId) {

        kafkaTemplate.send(
                KafkaTopics.ITEM_FAVORITED,
                itemId.toString(),
                event
        );

        log.info("Published ITEM_FAVORITED event for item {}", itemId);
    }

    public void publishItemUnfavorited(BaseEvent event, Long itemId) {

        kafkaTemplate.send(
                KafkaTopics.ITEM_UNFAVORITED,
                itemId.toString(),
                event
        );

        log.info("Published ITEM_UNFAVORITED event for item {}", itemId);
    }

}