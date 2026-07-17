package com.omnitrade.item_service.model.dto;

import com.omnitrade.item_service.model.enums.ItemCondition;
import com.omnitrade.item_service.model.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private Long id;
    private UUID sellerId;
    private String title;
    private String description;
    private BigDecimal price;
    private String city;
    private String district;
    private ItemCondition condition;
    private Long categoryId;
    private ItemStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer viewCount;
    private Integer favoriteCount;
    private Boolean allowTrade;
    private Boolean allowOffers;
}