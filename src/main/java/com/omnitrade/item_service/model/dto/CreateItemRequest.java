package com.omnitrade.item_service.model.dto;

import com.omnitrade.item_service.model.enums.ItemCondition;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {

        @NotBlank(message = "Title is required")
        @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
        private String title;

        @NotBlank(message = "Description is required")
        @Size(min = 20, max = 5000, message = "Description must be between 20 and 5000 characters")
        private String description;

        @NotNull(message = "Category is required")
        private Long categoryId;

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.00", inclusive = false)
        private BigDecimal price;

        @NotBlank(message = "City is required")
        @Size(max = 100)
        private String city;

        @NotBlank(message = "District is required")
        @Size(max = 100)
        private String district;

        @NotNull(message = "Condition is required")
        private ItemCondition condition;

        private Boolean allowTrade;

        private Boolean allowOffers;
}