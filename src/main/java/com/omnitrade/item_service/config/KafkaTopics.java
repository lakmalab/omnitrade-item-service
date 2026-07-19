package com.omnitrade.item_service.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KafkaTopics {

    public static final String ITEM_CREATED = "item.created";
    public static final String ITEM_UPDATED = "item.updated";
    public static final String ITEM_DELETED = "item.deleted";
    public static final String ITEM_VIEWED = "item.viewed";
    public static final String ITEM_FAVORITED = "item.favorited";
    public static final String ITEM_UNFAVORITED = "item.unfavorited";

    public static final String USER_DELETED = "user.deleted";
    public static final String USER_BLOCKED = "user.blocked";

    public static final String CATEGORY_DELETED = "category.deleted";
    public static final String CATEGORY_UPDATED = "category.updated";

    public static final String ITEM_DLT = "item.dlt";
    public static final String USER_DLT = "user.dlt";
    public static final String CATEGORY_DLT = "category.dlt";
}