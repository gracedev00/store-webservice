package com.store.repository;

import com.store.entity.PurchaseOrder;
import com.store.entity.OrderStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class PurchaseOrderRepository {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    public List<PurchaseOrder> findAll() {
        return em.createQuery("SELECT po FROM PurchaseOrder po", PurchaseOrder.class)
                .getResultList();
    }

    public Optional<PurchaseOrder> findById(Long id) {
        return Optional.ofNullable(em.find(PurchaseOrder.class, id));
    }

    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getId() == null) {
            em.persist(purchaseOrder);
            return purchaseOrder;
        } else {
            return em.merge(purchaseOrder);
        }
    }

    public void deleteById(Long id) {
        PurchaseOrder purchaseOrder = em.find(PurchaseOrder.class, id);
        if (purchaseOrder != null) {
            em.remove(purchaseOrder);
        }
    }

    public List<PurchaseOrder> findByCustomerId(Long customerId) {
        return em.createQuery("SELECT po FROM PurchaseOrder po WHERE po.customer.id = :customerId", PurchaseOrder.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public List<PurchaseOrder> findByStatus(OrderStatus status) {
        return em.createQuery("SELECT po FROM PurchaseOrder po WHERE po.status = :status", PurchaseOrder.class)
                .setParameter("status", status)
                .getResultList();
    }
}