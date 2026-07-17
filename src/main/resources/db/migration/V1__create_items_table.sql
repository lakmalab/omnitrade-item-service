-- V1__create_items_table.sql
-- Flyway migration for items table (MySQL)

CREATE TABLE IF NOT EXISTS items (
                                     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     seller_id CHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(15, 2) NOT NULL,
    city VARCHAR(100) NOT NULL,
    district VARCHAR(100),
    `condition` VARCHAR(20) NOT NULL,
    category_id BIGINT NOT NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    view_count INT DEFAULT 0,
    favorite_count INT DEFAULT 0,
    allow_trade BOOLEAN DEFAULT FALSE,
    allow_offers BOOLEAN DEFAULT FALSE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create indexes for better query performance
CREATE INDEX idx_items_seller_id ON items(seller_id);
CREATE INDEX idx_items_category_id ON items(category_id);
CREATE INDEX idx_items_status ON items(`status`);
CREATE INDEX idx_items_created_at ON items(created_at DESC);
CREATE INDEX idx_items_city ON items(city);
CREATE INDEX idx_items_city_district ON items(city, district);
CREATE INDEX idx_items_price ON items(price);
CREATE INDEX idx_items_condition ON items(`condition`);

-- Full-text index for title search (MySQL alternative to PostgreSQL's GIN)
CREATE FULLTEXT INDEX idx_items_title_fulltext ON items(title);

-- Add comments for documentation
ALTER TABLE items COMMENT = 'Stores product/item listings from sellers';
ALTER TABLE items MODIFY COLUMN id BIGINT COMMENT 'Unique identifier for the item';
ALTER TABLE items MODIFY COLUMN seller_id CHAR(36) COMMENT 'UUID of the seller from User Service';
ALTER TABLE items MODIFY COLUMN title VARCHAR(255) COMMENT 'Item title (5-100 characters)';
ALTER TABLE items MODIFY COLUMN description TEXT COMMENT 'Detailed item description (20-5000 characters)';
ALTER TABLE items MODIFY COLUMN price DECIMAL(15, 2) COMMENT 'Item price with 2 decimal places';
ALTER TABLE items MODIFY COLUMN city VARCHAR(100) COMMENT 'City where item is located';
ALTER TABLE items MODIFY COLUMN district VARCHAR(100) COMMENT 'District/neighborhood where item is located';
ALTER TABLE items MODIFY COLUMN `condition` VARCHAR(20) COMMENT 'Item condition: NEW, LIKE_NEW, GOOD, FAIR, POOR';
ALTER TABLE items MODIFY COLUMN category_id BIGINT COMMENT 'ID of the category from Category Service';
ALTER TABLE items MODIFY COLUMN `status` VARCHAR(20) COMMENT 'Item status: ACTIVE, SOLD, RESERVED, INACTIVE, DELETED';
ALTER TABLE items MODIFY COLUMN created_at TIMESTAMP COMMENT 'Timestamp when item was created';
ALTER TABLE items MODIFY COLUMN updated_at TIMESTAMP COMMENT 'Timestamp when item was last updated';
ALTER TABLE items MODIFY COLUMN view_count INT COMMENT 'Number of times item was viewed';
ALTER TABLE items MODIFY COLUMN favorite_count INT COMMENT 'Number of times item was favorited';
ALTER TABLE items MODIFY COLUMN allow_trade BOOLEAN COMMENT 'Whether seller accepts trades';
ALTER TABLE items MODIFY COLUMN allow_offers BOOLEAN COMMENT 'Whether seller accepts offers/bargaining';