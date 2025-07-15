package com.nhnhan.find_your_keeb.dto;

import com.nhnhan.find_your_keeb.entity.KeyboardLayout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterRequest {
    private KeyboardLayout layout;
    private String brand;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String searchTerm;
    private String switchType;
    private Boolean rgbSupport;
    private Boolean wirelessSupport;
    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "name";
    private String sortDirection = "asc";
} 