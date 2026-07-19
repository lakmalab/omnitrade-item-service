-- V1__create_items_table.sql
-- Flyway migration for items table (MySQL)

CREATE TABLE IF NOT EXISTS items (
                                     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'Unique identifier for the item',
                                     seller_id CHAR(36) NOT NULL COMMENT 'UUID of the seller from User Service',
    title VARCHAR(255) NOT NULL COMMENT 'Item title (5-100 characters)',
    description TEXT COMMENT 'Detailed item description (20-5000 characters)',
    price DECIMAL(15, 2) NOT NULL COMMENT 'Item price with 2 decimal places',
    city VARCHAR(100) NOT NULL COMMENT 'City where item is located',
    district VARCHAR(100) COMMENT 'District/neighborhood where item is located',
    `condition` VARCHAR(20) NOT NULL COMMENT 'Item condition: NEW, LIKE_NEW, GOOD, FAIR, POOR',
    category_id BIGINT NOT NULL COMMENT 'ID of the category from Category Service',
    `status` VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT 'Item status: ACTIVE, SOLD, RESERVED, INACTIVE, DELETED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp when item was created',
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp when item was last updated',
    view_count INT DEFAULT 0 COMMENT 'Number of times item was viewed',
    favorite_count INT DEFAULT 0 COMMENT 'Number of times item was favorited',
    allow_trade BOOLEAN DEFAULT FALSE COMMENT 'Whether seller accepts trades',
    allow_offers BOOLEAN DEFAULT FALSE COMMENT 'Whether seller accepts offers/bargaining'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Stores product/item listings from sellers';

-- Create indexes for better query performance
CREATE INDEX idx_items_seller_id ON items(seller_id);
CREATE INDEX idx_items_category_id ON items(category_id);
CREATE INDEX idx_items_status ON items(`status`);
CREATE INDEX idx_items_created_at ON items(created_at DESC);
CREATE INDEX idx_items_city ON items(city);
CREATE INDEX idx_items_city_district ON items(city, district);
CREATE INDEX idx_items_price ON items(price);
CREATE INDEX idx_items_condition ON items(`condition`);

-- Full-text index for title search
CREATE FULLTEXT INDEX idx_items_title_fulltext ON items(title);