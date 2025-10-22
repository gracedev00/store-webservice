package com.store.dto;

public class OrderItemDTO {
    private Long id;
    private Integer quantity;
    private Long productId;
    private String productName;
    private Double productPrice;

    // Constructeurs
    public OrderItemDTO() {}

    public OrderItemDTO(Integer quantity, Long productId, String productName, Double productPrice) {
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}