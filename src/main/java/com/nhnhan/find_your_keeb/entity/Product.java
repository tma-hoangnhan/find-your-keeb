package com.nhnhan.find_your_keeb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(nullable = false)
    private String brand;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KeyboardLayout layout;
    
    @Column(name = "switch_type")
    private String switchType;
    
    @Column(name = "keycap_material")
    private String keycapMaterial;
    
    @Column(name = "case_material")
    private String caseMaterial;
    
    @Column(name = "rgb_support")
    private Boolean rgbSupport = false;
    
    @Column(name = "wireless_support")
    private Boolean wirelessSupport = false;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
    
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
} 