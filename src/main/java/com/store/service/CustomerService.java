package com.store.service;

import com.store.entity.Customer;
import com.store.repository.CustomerRepository;
import jakarta.inject.Inject;
import java.util.List;

public class CustomerService {

    @Inject
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public void createCustomer(Customer customer) {
        customerRepository.create(customer);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.update(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.delete(id);
    }
}
