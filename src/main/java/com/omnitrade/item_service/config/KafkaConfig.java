package com.omnitrade.item_service.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private static final int PARTITIONS = 3;
    private static final short REPLICATION_FACTOR = 1;

    @Bean
    public NewTopic itemCreatedTopic() {
        return TopicBuilder.name(KafkaTopics.ITEM_CREATED)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic itemUpdatedTopic() {
        return TopicBuilder.name(KafkaTopics.ITEM_UPDATED)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic itemDeletedTopic() {
        return TopicBuilder.name(KafkaTopics.ITEM_DELETED)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic itemViewedTopic() {
        return TopicBuilder.name(KafkaTopics.ITEM_VIEWED)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic itemFavoritedTopic() {
        return TopicBuilder.name(KafkaTopics.ITEM_FAVORITED)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic itemUnfavoritedTopic() {
        return TopicBuilder.name(KafkaTopics.ITEM_UNFAVORITED)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic userDeletedTopic() {
        return TopicBuilder.name(KafkaTopics.USER_DELETED)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic userBlockedTopic() {
        return TopicBuilder.name(KafkaTopics.USER_BLOCKED)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic categoryDeletedTopic() {
        return TopicBuilder.name(KafkaTopics.CATEGORY_DELETED)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic categoryUpdatedTopic() {
        return TopicBuilder.name(KafkaTopics.CATEGORY_UPDATED)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic itemDltTopic() {
        return TopicBuilder.name(KafkaTopics.ITEM_DLT)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic userDltTopic() {
        return TopicBuilder.name(KafkaTopics.USER_DLT)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic categoryDltTopic() {
        return TopicBuilder.name(KafkaTopics.CATEGORY_DLT)
                .partitions(PARTITIONS)
                .replicas(REPLICATION_FACTOR)
                .build();
    }
}