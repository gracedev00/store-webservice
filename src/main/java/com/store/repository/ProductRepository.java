package com.store.repository;

import com.store.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class ProductRepository {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
            return product;
        } else {
            return em.merge(product);
        }
    }

    public void deleteById(Long id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
        }
    }

    public List<Product> findByName(String name) {
        return em.createQuery("SELECT p FROM Product p WHERE p.name LIKE :name", Product.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}