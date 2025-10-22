package com.store.repository;

import com.store.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class CustomerRepository {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class)
                .getResultList();
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(em.find(Customer.class, id));
    }

    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
            return customer;
        } else {
            return em.merge(customer);
        }
    }

    public void deleteById(Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
        }
    }

    public Optional<Customer> findByEmail(String email) {
        return em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }
}