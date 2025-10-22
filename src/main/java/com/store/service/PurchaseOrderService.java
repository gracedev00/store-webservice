package com.store.service;

import com.store.entity.PurchaseOrder;
import com.store.entity.OrderStatus;
import com.store.repository.PurchaseOrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PurchaseOrderService {

    @Inject
    PurchaseOrderRepository purchaseOrderRepository;

    @Inject
    CustomerService customerService;

    public List<PurchaseOrder> getAllOrders() {
        return purchaseOrderRepository.findAll();
    }

    public Optional<PurchaseOrder> getOrderById(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    public PurchaseOrder createOrder(PurchaseOrder order) {
        // Validation
        if (order.getCustomer() == null || order.getCustomer().getId() == null) {
            throw new IllegalArgumentException("Customer is required for order");
        }

        // Vérifier que le client existe
        customerService.getCustomerById(order.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + order.getCustomer().getId()));

        return purchaseOrderRepository.save(order);
    }

    public Optional<PurchaseOrder> updateOrderStatus(Long id, OrderStatus status) {
        return purchaseOrderRepository.findById(id)
                .map(existingOrder -> {
                    existingOrder.setStatus(status);
                    return purchaseOrderRepository.save(existingOrder);
                });
    }

    public boolean deleteOrder(Long id) {
        if (purchaseOrderRepository.findById(id).isPresent()) {
            purchaseOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<PurchaseOrder> getOrdersByCustomer(Long customerId) {
        return purchaseOrderRepository.findByCustomerId(customerId);
    }

    public List<PurchaseOrder> getOrdersByStatus(OrderStatus status) {
        return purchaseOrderRepository.findByStatus(status);
    }
}