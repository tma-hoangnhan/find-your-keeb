package com.nhnhan.find_your_keeb.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
} 