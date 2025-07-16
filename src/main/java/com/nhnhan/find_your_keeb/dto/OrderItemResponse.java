package com.nhnhan.find_your_keeb.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private Long productId;
    private String imageUrl;
    private String brand;
    private String layout;
    private String switchType;
    private String keycapMaterial;
    private String caseMaterial;
    private Boolean rgbSupport;
    private Boolean wirelessSupport;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getLayout() { return layout; }
    public void setLayout(String layout) { this.layout = layout; }
    public String getSwitchType() { return switchType; }
    public void setSwitchType(String switchType) { this.switchType = switchType; }
    public String getKeycapMaterial() { return keycapMaterial; }
    public void setKeycapMaterial(String keycapMaterial) { this.keycapMaterial = keycapMaterial; }
    public String getCaseMaterial() { return caseMaterial; }
    public void setCaseMaterial(String caseMaterial) { this.caseMaterial = caseMaterial; }
    public Boolean getRgbSupport() { return rgbSupport; }
    public void setRgbSupport(Boolean rgbSupport) { this.rgbSupport = rgbSupport; }
    public Boolean getWirelessSupport() { return wirelessSupport; }
    public void setWirelessSupport(Boolean wirelessSupport) { this.wirelessSupport = wirelessSupport; }
} 