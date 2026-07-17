package com.omnitrade.item_service.controller;

import com.omnitrade.item_service.model.dto.CreateItemRequest;
import com.omnitrade.item_service.model.dto.ItemResponse;

import com.omnitrade.item_service.model.dto.CreateItemRequest;
import com.omnitrade.item_service.model.dto.UpdateItemRequest;
import com.omnitrade.item_service.model.dto.ItemResponse;
import com.omnitrade.item_service.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(
            @Valid @RequestBody CreateItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.createItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemRequest request) {
        if (!id.equals(request.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(itemService.updateItem(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long id,
            @RequestParam UUID sellerId) {
        itemService.deleteItem(id, sellerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Page<ItemResponse>> getItemsBySeller(
            @PathVariable UUID sellerId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(itemService.getItemsBySeller(sellerId, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponse>> getActiveItems(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(itemService.getActiveItems(pageable));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ItemResponse>> getItemsByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(itemService.getItemsByCategory(categoryId, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ItemResponse>> searchItems(
            @RequestParam String keyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(itemService.searchItems(keyword, pageable));
    }

    @GetMapping("/location")
    public ResponseEntity<Page<ItemResponse>> getItemsByLocation(
            @RequestParam String city,
            @RequestParam(required = false) String district,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        if (district != null && !district.isEmpty()) {
            return ResponseEntity.ok(itemService.getItemsByCityAndDistrict(city, district, pageable));
        }
        return ResponseEntity.ok(itemService.getItemsByCity(city, pageable));
    }

    @GetMapping("/price-range")
    public ResponseEntity<Page<ItemResponse>> getItemsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(itemService.getItemsByPriceRange(minPrice, maxPrice, pageable));
    }

    @GetMapping("/top-viewed")
    public ResponseEntity<Page<ItemResponse>> getTopViewedItems(
            @PageableDefault(size = 10, sort = "viewCount", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(itemService.getTopViewedItems(pageable));
    }

    @GetMapping("/recent")
    public ResponseEntity<Page<ItemResponse>> getRecentItems(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(itemService.getRecentItems(pageable));
    }

    @PostMapping("/{id}/favorite")
    public ResponseEntity<Void> incrementFavoriteCount(@PathVariable Long id) {
        itemService.incrementFavoriteCount(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/favorite")
    public ResponseEntity<Void> decrementFavoriteCount(@PathVariable Long id) {
        itemService.decrementFavoriteCount(id);
        return ResponseEntity.ok().build();
    }
}