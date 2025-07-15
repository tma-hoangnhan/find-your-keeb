package com.nhnhan.find_your_keeb.repository;

import com.nhnhan.find_your_keeb.entity.KeyboardLayout;
import com.nhnhan.find_your_keeb.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Filter by layout
    Page<Product> findByLayout(KeyboardLayout layout, Pageable pageable);
    
    // Filter by brand
    Page<Product> findByBrand(String brand, Pageable pageable);
    
    // Filter by price range
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    // Filter by layout and brand
    Page<Product> findByLayoutAndBrand(KeyboardLayout layout, String brand, Pageable pageable);
    
    // Filter by layout and price range
    Page<Product> findByLayoutAndPriceBetween(KeyboardLayout layout, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    // Filter by brand and price range
    Page<Product> findByBrandAndPriceBetween(String brand, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    // Filter by layout, brand, and price range
    Page<Product> findByLayoutAndBrandAndPriceBetween(KeyboardLayout layout, String brand, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    // Search by name containing
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Get all brands
    @Query("SELECT DISTINCT p.brand FROM Product p")
    List<String> findAllBrands();
    
    // Get products with stock
    Page<Product> findByStockQuantityGreaterThan(Integer quantity, Pageable pageable);
    
    // Get products by switch type
    Page<Product> findBySwitchType(String switchType, Pageable pageable);
    
    // Get products with RGB support
    Page<Product> findByRgbSupportTrue(Pageable pageable);
    
    // Get wireless keyboards
    Page<Product> findByWirelessSupportTrue(Pageable pageable);
} 