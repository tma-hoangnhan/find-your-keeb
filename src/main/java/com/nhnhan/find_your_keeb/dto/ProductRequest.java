package com.nhnhan.find_your_keeb.dto;

import com.nhnhan.find_your_keeb.entity.KeyboardLayout;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 255, message = "Product name must be between 2 and 255 characters")
    private String name;
    
    @NotBlank(message = "Product description is required")
    @Size(min = 10, max = 2000, message = "Product description must be between 10 and 2000 characters")
    private String description;
    
    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @DecimalMax(value = "9999.99", message = "Price must be less than 10000")
    private BigDecimal price;
    
    @NotBlank(message = "Product brand is required")
    @Size(min = 2, max = 100, message = "Brand must be between 2 and 100 characters")
    private String brand;
    
    @NotNull(message = "Keyboard layout is required")
    private KeyboardLayout layout;
    
    @NotBlank(message = "Switch type is required")
    @Size(min = 2, max = 100, message = "Switch type must be between 2 and 100 characters")
    private String switchType;
    
    @NotBlank(message = "Keycap material is required")
    @Size(min = 2, max = 100, message = "Keycap material must be between 2 and 100 characters")
    private String keycapMaterial;
    
    @NotBlank(message = "Case material is required")
    @Size(min = 2, max = 100, message = "Case material must be between 2 and 100 characters")
    private String caseMaterial;
    
    @NotNull(message = "RGB support status is required")
    private Boolean rgbSupport;
    
    @NotNull(message = "Wireless support status is required")
    private Boolean wirelessSupport;
    
    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Max(value = 9999, message = "Stock quantity cannot exceed 9999")
    private Integer stockQuantity;
    
    @NotBlank(message = "Image URL is required")
    private String imageUrl;
} 