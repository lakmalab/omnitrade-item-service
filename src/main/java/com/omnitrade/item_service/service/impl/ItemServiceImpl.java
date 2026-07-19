package com.omnitrade.item_service.service.impl;

import com.omnitrade.item_service.exception.ResourceNotFoundException;
import com.omnitrade.item_service.exception.UnauthorizedException;
import com.omnitrade.item_service.kafka.mapper.ItemEventMapper;
import com.omnitrade.item_service.kafka.producer.ItemEventProducer;
import com.omnitrade.item_service.model.dto.CreateItemRequest;
import com.omnitrade.item_service.model.dto.UpdateItemRequest;
import com.omnitrade.item_service.model.dto.ItemResponse;
import com.omnitrade.item_service.model.entity.Item;
import com.omnitrade.item_service.model.enums.ItemStatus;
import com.omnitrade.item_service.repository.ItemRepository;
import com.omnitrade.item_service.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemEventProducer eventProducer;
    private final ItemEventMapper eventMapper;

    @Override
    public ItemResponse createItem(CreateItemRequest request) {
        log.info("Creating new item with title: {}", request.getTitle());

        UUID sellerId = getCurrentUserId();

        Item item = Item.builder()
                .sellerId(sellerId)
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .city(request.getCity())
                .district(request.getDistrict())
                .condition(request.getCondition())
                .categoryId(request.getCategoryId())
                .status(ItemStatus.ACTIVE)
                .viewCount(0)
                .favoriteCount(0)
                .allowTrade(request.getAllowTrade() != null && request.getAllowTrade())
                .allowOffers(request.getAllowOffers() != null && request.getAllowOffers())
                .build();

        Item savedItem = itemRepository.save(item);
        eventProducer.publishItemCreated(
                eventMapper.toItemCreatedEvent(savedItem),
                savedItem.getId()
        );

        log.info("Item created successfully with ID: {}", savedItem.getId());

        return mapToResponse(savedItem);
    }

    @Override
    @CachePut(
            value = "items",
            key = "#result.id"
    )
    public ItemResponse updateItem(UpdateItemRequest request) {
        log.info("Updating item with ID: {}", request.getId());

        Item existingItem = itemRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + request.getId()));

        UUID currentUserId = getCurrentUserId();
        if (!existingItem.getSellerId().equals(currentUserId)) {
            throw new UnauthorizedException("You are not authorized to update this item");
        }

        existingItem.setTitle(request.getTitle());
        existingItem.setDescription(request.getDescription());
        existingItem.setCategoryId(request.getCategoryId());
        existingItem.setPrice(request.getPrice());
        existingItem.setCity(request.getCity());
        existingItem.setDistrict(request.getDistrict());
        existingItem.setCondition(request.getCondition());
        existingItem.setAllowTrade(request.getAllowTrade() != null && request.getAllowTrade());
        existingItem.setAllowOffers(request.getAllowOffers() != null && request.getAllowOffers());

        Item updatedItem = itemRepository.save(existingItem);
        eventProducer.publishItemUpdated(
                eventMapper.toItemUpdatedEvent(updatedItem),
                updatedItem.getId()
        );
        log.info("Item updated successfully with ID: {}", updatedItem.getId());

        return mapToResponse(updatedItem);
    }

    @Override
    @Cacheable(
            value = "items",
            key = "#id"
    )
    public ItemResponse getItemById(Long id) {
        log.info("Fetching item with ID: {}", id);

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + id));

        incrementViewCount(id);

        return mapToResponse(item);
    }

    @Override
    @CacheEvict(
            value = "items",
            key = "#id"
    )
    public void deleteItem(Long id, UUID sellerId) {
        log.info("Deleting item with ID: {}", id);

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + id));

        if (!item.getSellerId().equals(sellerId)) {
            throw new UnauthorizedException("You are not authorized to delete this item");
        }

        item.setStatus(ItemStatus.DELETED);

        Item deletedItem = itemRepository.save(item);

        eventProducer.publishItemDeleted(
                eventMapper.toItemDeletedEvent(deletedItem),
                deletedItem.getId()
        );
        log.info("Item deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> getItemsBySeller(UUID sellerId, Pageable pageable) {
        log.info("Fetching items for seller: {}", sellerId);
        return itemRepository.findBySellerId(sellerId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "activeItems"
    )
    public Page<ItemResponse> getActiveItems(Pageable pageable) {
        log.info("Fetching active items");
        return itemRepository.findByStatus(ItemStatus.ACTIVE, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "itemsByCategory"
    )
    public Page<ItemResponse> getItemsByCategory(Long categoryId, Pageable pageable) {
        log.info("Fetching items for category: {}", categoryId);
        return itemRepository.findByCategoryId(categoryId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "searchResults"
    )
    public Page<ItemResponse> searchItems(String keyword, Pageable pageable) {
        log.info("Searching items with keyword: {}", keyword);
        return itemRepository.findByTitleContainingIgnoreCaseAndStatus(keyword, ItemStatus.ACTIVE, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "itemsByCity"
    )
    public Page<ItemResponse> getItemsByCity(String city, Pageable pageable) {
        log.info("Fetching items for city: {}", city);
        return itemRepository.findByCityIgnoreCaseAndStatus(city, ItemStatus.ACTIVE, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "itemsByCityAndDistrict"
    )
    public Page<ItemResponse> getItemsByCityAndDistrict(String city, String district, Pageable pageable) {
        log.info("Fetching items for city: {} and district: {}", city, district);
        return itemRepository.findByCityIgnoreCaseAndDistrictIgnoreCaseAndStatus(city, district, ItemStatus.ACTIVE, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "itemsByPriceRange"
    )
    public Page<ItemResponse> getItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        log.info("Fetching items in price range: {} - {}", minPrice, maxPrice);
        return itemRepository.findByPriceBetweenAndStatus(minPrice, maxPrice, ItemStatus.ACTIVE, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public void incrementViewCount(Long itemId) {
        itemRepository.incrementViewCount(itemId);

        itemRepository.findById(itemId)
                .ifPresent(item ->
                        eventProducer.publishItemViewed(
                                eventMapper.toItemViewedEvent(item),
                                item.getId()
                        ));
    }

    @Override
    public void incrementFavoriteCount(Long itemId) {
        itemRepository.incrementFavoriteCount(itemId);

        itemRepository.findById(itemId)
                .ifPresent(item ->
                        eventProducer.publishItemFavorited(
                                eventMapper.toItemFavoritedEvent(
                                        item,
                                        getCurrentUserId()
                                ),
                                item.getId()
                        ));
    }

    @Override
    public void decrementFavoriteCount(Long itemId) {
        itemRepository.decrementFavoriteCount(itemId);

        itemRepository.findById(itemId)
                .ifPresent(item ->
                        eventProducer.publishItemUnfavorited(
                                eventMapper.toItemUnfavoritedEvent(
                                        item,
                                        getCurrentUserId()
                                ),
                                item.getId()
                        ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> getTopViewedItems(Pageable pageable) {
        log.info("Fetching top viewed items");
        return itemRepository.findTopViewedItems(ItemStatus.ACTIVE, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> getRecentItems(Pageable pageable) {
        log.info("Fetching recent items");
        return itemRepository.findRecentItems(ItemStatus.ACTIVE, pageable)
                .map(this::mapToResponse);
    }

    private ItemResponse mapToResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .sellerId(item.getSellerId())
                .title(item.getTitle())
                .description(item.getDescription())
                .price(item.getPrice())
                .city(item.getCity())
                .district(item.getDistrict())
                .condition(item.getCondition())
                .categoryId(item.getCategoryId())
                .status(item.getStatus())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .viewCount(item.getViewCount())
                .favoriteCount(item.getFavoriteCount())
                .allowTrade(item.getAllowTrade())
                .allowOffers(item.getAllowOffers())
                .build();
    }

    // helper method to get current user ID from security context, get this from SecurityContext when keyclock implemented
    private UUID getCurrentUserId() {
        return UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    }
}