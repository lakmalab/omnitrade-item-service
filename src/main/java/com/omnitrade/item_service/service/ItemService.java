package com.omnitrade.item_service.service;

import com.omnitrade.item_service.model.dto.CreateItemRequest;
import com.omnitrade.item_service.model.dto.ItemResponse;
import com.omnitrade.item_service.model.dto.UpdateItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface ItemService {

    ItemResponse createItem(CreateItemRequest request);

    ItemResponse updateItem(UpdateItemRequest request);

    ItemResponse getItemById(Long id);

    void deleteItem(Long id, UUID sellerId);

    Page<ItemResponse> getItemsBySeller(UUID sellerId, Pageable pageable);

    Page<ItemResponse> getActiveItems(Pageable pageable);

    Page<ItemResponse> getItemsByCategory(Long categoryId, Pageable pageable);

    Page<ItemResponse> searchItems(String keyword, Pageable pageable);

    Page<ItemResponse> getItemsByCity(String city, Pageable pageable);

    Page<ItemResponse> getItemsByCityAndDistrict(String city, String district, Pageable pageable);

    Page<ItemResponse> getItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    void incrementViewCount(Long itemId);

    void incrementFavoriteCount(Long itemId);

    void decrementFavoriteCount(Long itemId);

    Page<ItemResponse> getTopViewedItems(Pageable pageable);

    Page<ItemResponse> getRecentItems(Pageable pageable);
}
