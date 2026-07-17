package com.omnitrade.item_service.repository;

import com.omnitrade.item_service.model.enums.ItemStatus;
import com.omnitrade.item_service.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // Find by seller ID
    Page<Item> findBySellerId(UUID sellerId, Pageable pageable);

    // Find by status
    Page<Item> findByStatus(ItemStatus status, Pageable pageable);

    // Find by category ID
    Page<Item> findByCategoryId(Long categoryId, Pageable pageable);

    // Find by seller and status
    Page<Item> findBySellerIdAndStatus(UUID sellerId, ItemStatus status, Pageable pageable);

    // Search by title containing keyword (case-insensitive)
    Page<Item> findByTitleContainingIgnoreCaseAndStatus(String keyword, ItemStatus status, Pageable pageable);

    // Find by city
    Page<Item> findByCityIgnoreCaseAndStatus(String city, ItemStatus status, Pageable pageable);

    // Find by city and district
    Page<Item> findByCityIgnoreCaseAndDistrictIgnoreCaseAndStatus(String city, String district, ItemStatus status, Pageable pageable);

    // Find by price range
    Page<Item> findByPriceBetweenAndStatus(BigDecimal minPrice, BigDecimal maxPrice, ItemStatus status, Pageable pageable);

    // Find by category and price range
    Page<Item> findByCategoryIdAndPriceBetweenAndStatus(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, ItemStatus status, Pageable pageable);

    // Increment view count
    @Modifying
    @Query("UPDATE Item i SET i.viewCount = i.viewCount + 1 WHERE i.id = :itemId")
    void incrementViewCount(@Param("itemId") Long itemId);

    // Increment favorite count
    @Modifying
    @Query("UPDATE Item i SET i.favoriteCount = i.favoriteCount + 1 WHERE i.id = :itemId")
    void incrementFavoriteCount(@Param("itemId") Long itemId);

    // Decrement favorite count
    @Modifying
    @Query("UPDATE Item i SET i.favoriteCount = i.favoriteCount - 1 WHERE i.id = :itemId AND i.favoriteCount > 0")
    void decrementFavoriteCount(@Param("itemId") Long itemId);

    // Check if item belongs to seller
    @Query("SELECT COUNT(i) > 0 FROM Item i WHERE i.id = :itemId AND i.sellerId = :sellerId")
    boolean existsByIdAndSellerId(@Param("itemId") Long itemId, @Param("sellerId") UUID sellerId);

    // Find active items by seller
    List<Item> findBySellerIdAndStatus(UUID sellerId, ItemStatus status);

    // Get top viewed items
    @Query("SELECT i FROM Item i WHERE i.status = :status ORDER BY i.viewCount DESC")
    Page<Item> findTopViewedItems(@Param("status") ItemStatus status, Pageable pageable);

    // Get recently added items
    @Query("SELECT i FROM Item i WHERE i.status = :status ORDER BY i.createdAt DESC")
    Page<Item> findRecentItems(@Param("status") ItemStatus status, Pageable pageable);
}