package com.store.service;

import com.store.entity.Customer;
import com.store.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        // Validation métier
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        // Vérifier si l'email existe déjà
        customerRepository.findByEmail(customer.getEmail())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Email already exists: " + customer.getEmail());
                });

        return customerRepository.save(customer);
    }

    public Optional<Customer> updateCustomer(Long id, Customer customerDetails) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setName(customerDetails.getName());
                    existingCustomer.setEmail(customerDetails.getEmail());
                    return customerRepository.save(existingCustomer);
                });
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}