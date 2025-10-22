package com.store.mapper;

import com.store.entity.PurchaseOrder;
import com.store.entity.OrderItem;
import com.store.dto.OrderDTO;
import com.store.dto.OrderItemDTO;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(PurchaseOrder entity) {
        if (entity == null) {
            return null;
        }

        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setStatus(entity.getStatus());

        if (entity.getCustomer() != null) {
            dto.setCustomerId(entity.getCustomer().getId());
            dto.setCustomerName(entity.getCustomer().getName());
        }

        if (entity.getItems() != null) {
            dto.setItems(entity.getItems().stream()
                    .map(OrderMapper::toItemDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static PurchaseOrder toEntity(OrderDTO dto) {
        if (dto == null) {
            return null;
        }

        PurchaseOrder entity = new PurchaseOrder();
        entity.setId(dto.getId());
        entity.setOrderDate(dto.getOrderDate());
        entity.setStatus(dto.getStatus());

        return entity;
    }

    private static OrderItemDTO toItemDTO(OrderItem entity) {
        if (entity == null) {
            return null;
        }

        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());

        if (entity.getProduct() != null) {
            dto.setProductId(entity.getProduct().getId());
            dto.setProductName(entity.getProduct().getName());
            dto.setProductPrice(entity.getProduct().getPrice());
        }

        return dto;
    }

    public static OrderItem toItemEntity(OrderItemDTO dto) {
        if (dto == null) {
            return null;
        }

        OrderItem entity = new OrderItem();
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());

        return entity;
    }
}