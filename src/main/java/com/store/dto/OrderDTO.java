package com.store.dto;

import com.store.entity.OrderStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private Long id;
    private LocalDate orderDate;
    private OrderStatus status;
    private Long customerId;
    private String customerName;
    private List<OrderItemDTO> items = new ArrayList<>();

    // Constructeurs
    public OrderDTO() {}

    public OrderDTO(Long id, LocalDate orderDate, OrderStatus status, Long customerId, String customerName) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}